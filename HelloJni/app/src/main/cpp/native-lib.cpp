//
// Created by 彭旭锐 on 5/5/2021.
//

#include "com_xurui_hellojni_HelloWorld.h"

#include <android/log.h>

#define TAG "Peng"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)


extern "C"
JNIEXPORT void JNICALL
Java_com_xurui_hellojni_HelloWorld_sayHi(JNIEnv *env, jobject thiz) {
    LOGD("sayHi:%s", "HelloWorld!");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_xurui_hellojni_HelloWorld_accessField(JNIEnv *env, jobject thiz) {
    // 获取 jclass
    jclass clz = env->GetObjectClass(thiz);
    // 示例：修改 Java 静态变量值
    // 静态字段 ID
    jfieldID sFieldId = env->GetStaticFieldID(clz, "sName", "Ljava/lang/String;");
    // 访问静态字段
    if (sFieldId) {
        // Java 方法的返回值 String 映射为 jstring
        jstring jStr = static_cast<jstring>(env->GetStaticObjectField(clz, sFieldId));
        // 将 jstring 转换为 C 风格字符串
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        LOGD("静态字段：%s", sStr);
        // 释放资源
        env->ReleaseStringUTFChars(jStr, sStr);
        // 构造 jstring
        jstring newStr = env->NewStringUTF("静态字段 - Peng");
        if (newStr) {
            // jstring 本身就是 Java String 的映射，可以直接传递到 Java 层
            env->SetStaticObjectField(clz, sFieldId, newStr);
        }
    }
    // 示例：修改 Java 成员变量值
    // 实例字段 ID
    jfieldID mFieldId = env->GetFieldID(clz, "mName", "Ljava/lang/String;");
    // 访问实例字段
    if (mFieldId) {
        jstring jStr = static_cast<jstring>(env->GetObjectField(thiz, mFieldId));
        // 转换为 C 字符串
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        LOGD("实例字段：%s", sStr);
        // 释放资源
        env->ReleaseStringUTFChars(jStr, sStr);
        // 构造 jstring
        jstring newStr = env->NewStringUTF("实例字段 - Peng");
        if (newStr) {
            // jstring 本身就是 Java String 的映射，可以直接传递到 Java 层
            env->SetObjectField(thiz, mFieldId, newStr);
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_xurui_hellojni_HelloWorld_accessMethod(JNIEnv *env, jobject thiz) {
    // 获取 jclass
    jclass clz = env->GetObjectClass(thiz);
    // 示例：调用 Java 静态方法
    // 静态方法 ID
    jmethodID sMethodId = env->GetStaticMethodID(clz, "sHelloJava", "()V");
    if (sMethodId) {
        env->CallStaticVoidMethod(clz, sMethodId);
    }
    // 示例：调用 Java 实例方法
    // 实例方法 ID
    jmethodID mMethodId = env->GetMethodID(clz, "helloJava", "()V");
    if (mMethodId) {
        env->CallVoidMethod(thiz, mMethodId);
    }
}