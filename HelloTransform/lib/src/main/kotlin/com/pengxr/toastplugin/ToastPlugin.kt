package com.pengxr.toastplugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ToastPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 获取 Android 扩展
        val androidExtension = project.extensions.getByType(BaseExtension::class.java)
        // 注册 Transform，支持额外增加依赖
        androidExtension.registerTransform(ToastTransform(project)/* 支持增加依赖*/)
    }
}