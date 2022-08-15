package com.pengxr.toastplugin

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.builder.utils.isValidZipEntryName
import com.android.utils.FileUtils
import com.google.common.io.Files
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Created by pengxr on 13/5/2022
 */
abstract class BaseCustomTransform(private val debug: Boolean) : Transform() {

    abstract fun provideFunction(): ((InputStream, OutputStream) -> Unit)?

    open fun classFilter(className: String) = className.endsWith(SdkConstants.DOT_CLASS)

    override fun isIncremental() = true

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)

        log("Transform start, isIncremental = ${transformInvocation.isIncremental}.")

        val inputProvider = transformInvocation.inputs
        val referenceProvider = transformInvocation.referencedInputs
        val outputProvider = transformInvocation.outputProvider

        // 1. Transform logic implemented by subclasses.
        val function = provideFunction()

        // 2. Delete all transform tmp files when not in incremental build.
        if (!transformInvocation.isIncremental) {
            log("All File deleted.")
            outputProvider.deleteAll()
        }

        for (input in inputProvider) {
            // 3. Transform jar input.
            log("Transform jarInputs start.")
            for (jarInput in input.jarInputs) {
                val inputJar = jarInput.file
                val outputJar = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                if (transformInvocation.isIncremental) {
                    // 3.1 Transform jar input in incremental build.
                    when (jarInput.status ?: Status.NOTCHANGED) {
                        Status.NOTCHANGED -> {
                            // Do nothing.
                        }
                        Status.ADDED, Status.CHANGED -> {
                            // Do transform.
                            transformJar(inputJar, outputJar, function)
                        }
                        Status.REMOVED -> {
                            // Delete.
                            FileUtils.delete(outputJar)
                        }
                    }
                } else {
                    // 3.2 Transform jar input in full build.
                    transformJar(inputJar, outputJar, function)
                }
            }
            // 4. Transform dir input.
            log("Transform dirInput start.")
            for (dirInput in input.directoryInputs) {
                val inputDir = dirInput.file
                val outputDir = outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                if (transformInvocation.isIncremental) {
                    // 4.1 Transform dir input in incremental build.
                    for ((inputFile, status) in dirInput.changedFiles) {
                        val outputFile = concatOutputFilePath(outputDir, inputFile)
                        when (status ?: Status.NOTCHANGED) {
                            Status.NOTCHANGED -> {
                                // Do nothing.
                            }
                            Status.ADDED, Status.CHANGED -> {
                                // Do transform.
                                doTransformFile(inputFile, outputFile, function)
                            }
                            Status.REMOVED -> {
                                // Delete
                                FileUtils.delete(outputFile)
                            }
                        }
                    }
                } else {
                    // 4.2 Transform dir input in full build.
                    // Traversal fileTree (depthFirstPreOrder).
                    for (inputFile in FileUtils.getAllFiles(inputDir)) {
                        val outputFile = concatOutputFilePath(outputDir, inputFile)
                        if (classFilter(inputFile.name)) {
                            doTransformFile(inputFile, outputFile, function)
                        } else {
                            // Copy.
                            Files.createParentDirs(outputFile)
                            FileUtils.copyFile(inputFile, outputFile)
                        }
                    }
                }
            }
        }
        log("Transform end.")
    }

    /**
     * Do transform Jar.
     */
    private fun transformJar(inputJar: File, outputJar: File, function: ((InputStream, OutputStream) -> Unit)?) {
        // Create parent directories to hold outputJar file.
        Files.createParentDirs(outputJar)
        // Unzip.
        FileInputStream(inputJar).use { fis ->
            ZipInputStream(fis).use { zis ->
                // Zip.
                FileOutputStream(outputJar).use { fos ->
                    ZipOutputStream(fos).use { zos ->
                        var entry = zis.nextEntry
                        while (entry != null && isValidZipEntryName(entry)) {
                            if (!entry.isDirectory) {
                                zos.putNextEntry(ZipEntry(entry.name))
                                if (classFilter(entry.name)) {
                                    // Apply transform function.
                                    applyFunction(zis, zos, function)
                                } else {
                                    // Copy.
                                    zis.copyTo(zos)
                                }
                            }
                            entry = zis.nextEntry
                        }
                    }
                }
            }
        }
    }

    /**
     * Do transform file.
     */
    private fun doTransformFile(inputFile: File, outputFile: File, function: ((InputStream, OutputStream) -> Unit)?) {
        // Create parent directories to hold outputFile file.
        Files.createParentDirs(outputFile)
        FileInputStream(inputFile).use { fis ->
            FileOutputStream(outputFile).use { fos ->
                // Apply transform function.
                applyFunction(fis, fos, function)
            }
        }
    }

    private fun concatOutputFilePath(outputDir: File, inputFile: File) = File(outputDir, inputFile.name)

    private fun applyFunction(input: InputStream, output: OutputStream, function: ((InputStream, OutputStream) -> Unit)?) {
        try {
            if (null != function) {
                function.invoke(input, output)
            } else {
                // Copy
                input.copyTo(output)
            }
        } catch (e: UncheckedIOException) {
            throw e.cause!!
        }
    }

    private fun log(logStr: String) {
        if (debug) {
            println("$name - $logStr")
        }
    }
}