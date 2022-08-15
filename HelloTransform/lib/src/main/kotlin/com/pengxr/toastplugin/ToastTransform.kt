package com.pengxr.toastplugin

import com.android.build.api.variant.VariantInfo
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.NotFoundException
import javassist.bytecode.AnnotationsAttribute
import org.gradle.api.Incubating
import org.gradle.api.Project
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by pengxr on 11/5/2022
 */
internal class ToastTransform(val project: Project) : BaseCustomTransform(true) {

    override fun getName() = "ToastTransform"

    override fun isIncremental() = true

    override fun classFilter(className: String) = className.endsWith("Activity.class")

    /**
     * 用于过滤 Variant，返回 false 表示该 Variant 不执行 Transform
     */
    @Incubating
    override fun applyToVariant(variant: VariantInfo?): Boolean {
        return "debug" == variant?.buildTypeName
    }

    override fun getInputTypes() = TransformManager.CONTENT_CLASS

    override fun getScopes() = TransformManager.SCOPE_FULL_PROJECT

    override fun provideFunction() = { ios: InputStream, zos: OutputStream ->
        val classPool = ClassPool.getDefault()
        // 加入android.jar
        classPool.appendClassPath((project.extensions.getByName("android") as BaseExtension).bootClasspath[0].toString())
        classPool.importPackage("android.os.Bundle")
        // Input
        val ctClass = classPool.makeClass(ios)
        try {
            ctClass.getDeclaredMethod("onCreate").also {
                println("onCreate found in ${ctClass.simpleName}")
                val attribute = it.methodInfo.getAttribute(AnnotationsAttribute.invisibleTag) as? AnnotationsAttribute
                if (null != attribute?.getAnnotation("com.pengxr.hellotransform.Hello")) {
                    println("Insert toast in ${ctClass.simpleName}")
                    it.insertAfter(
                        """android.widget.Toast.makeText(this,"Hello Transform!",android.widget.Toast.LENGTH_SHORT).show();  
                                      """
                    )
                }
            }
        } catch (e: NotFoundException) {
            // ignore
        }
        // Output
        zos.write(ctClass.toBytecode())
        ctClass.detach()
    }
}