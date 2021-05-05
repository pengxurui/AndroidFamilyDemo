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
    // 静态字段 ID
    jfieldID sFieldId = env->GetStaticFieldID(clz, "sName", "Ljava/lang/String;");
    // 访问静态字段
    if (sFieldId) {
        jstring jStr = static_cast<jstring>(env->GetStaticObjectField(clz, sFieldId));
        // 转换为 C 字符串
        const char *sStr = env->GetStringUTFChars(jStr, NULL);
        LOGD("静态字段：%s", sStr);
        env->ReleaseStringUTFChars(jStr, sStr);
        jstring newStr = env->NewStringUTF("静态字段 - Peng");
        if (newStr) {
            env->SetStaticObjectField(clz, sFieldId, newStr);
        }
    }
    // 实例字段 ID
    jfieldID mFieldId = env->GetFieldID(clz, "mName", "Ljava/lang/String;");
    // 访问实例字段
    if (mFieldId) {
        jstring jStr = static_cast<jstring>(env->GetObjectField(thiz, mFieldId));
        // 转换为 C 字符串
        const char *sStr = env->GetStringUTFChars(jStr, NULL);
        LOGD("实例字段：%s", sStr);
        env->ReleaseStringUTFChars(jStr, sStr);
        jstring newStr = env->NewStringUTF("实例字段 - Peng");
        if (newStr) {
            env->SetObjectField(thiz, mFieldId, newStr);
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_xurui_hellojni_HelloWorld_accessMethod(JNIEnv *env, jobject thiz) {
    // 获取 jclass
    jclass clz = env->GetObjectClass(thiz);
    // 静态方法 ID
    jmethodID sMethodId = env->GetStaticMethodID(clz, "sHelloJava", "()V");
    if (sMethodId) {
        env->CallStaticVoidMethod(clz, sMethodId);
    }
    // 实例方法 ID
    jmethodID mMethodId = env->GetMethodID(clz, "helloJava", "()V");
    if (mMethodId) {
        env->CallVoidMethod(thiz, mMethodId);
    }
}