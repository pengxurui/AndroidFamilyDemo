//
// Created by 彭旭锐 on 5/5/2021.
//


#include <android/log.h>
#include <jni.h>
#include <string>

#define TAG "HelloJNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_xurui_hellojni_HelloWorld_sayHi(JNIEnv *env, jobject thiz) {
    LOGD("%s", "Java 调用 Native 方法 sayHi：HelloWorld!");
}

// 示例：访问 Java 字段
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
        // 将 jstring 转换为 C/C++ 字符串
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        LOGD("静态字段：%s", sStr);
        // 释放资源
        env->ReleaseStringUTFChars(jStr, sStr);
        // 构造 Java String 对象（将 C/C++ 字符串转换为 Java String）
        jstring newStr = env->NewStringUTF("修改值");
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
        // 转换为 C/C++ 字符串
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        LOGD("实例字段：%s", sStr);
        // 释放资源
        env->ReleaseStringUTFChars(jStr, sStr);
        // 构造 jstring
        jstring newStr = env->NewStringUTF("修改值");
        if (newStr) {
            // jstring 本身就是 Java String 的映射，可以直接传递到 Java 层
            env->SetObjectField(thiz, mFieldId, newStr);
        }
    }
}

// 示例：调用 Java 方法
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

// 示例：操作基本类型数组
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_xurui_hellojni_HelloWorld_generateIntArray(JNIEnv *env, jobject thiz, jint size) {
    // 新建 Java int[]
    jintArray jarr = env->NewIntArray(size);
    // 转换为 C/C ++ int[]
    int *carr = env->GetIntArrayElements(jarr, JNI_FALSE);
    // 赋值
    for (int i = 0; i < size; i++) {
        carr[i] = i;
    }
    // 释放资源并回写
    env->ReleaseIntArrayElements(jarr, carr, 0);
    // 返回数组
    return jarr;
}

// 示例：操作引用类型数组
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_xurui_hellojni_HelloWorld_generateStringArray(JNIEnv *env, jobject thiz, jint size) {
    // 获取 String Class
    jclass jStringClazz = env->FindClass("java/lang/String");
    // 初始值（可为空）
    jstring initialStr = env->NewStringUTF("初始值");
    // 创建 Java String[]
    jobjectArray jarr = env->NewObjectArray(size, jStringClazz, initialStr);
    // 赋值
    for (int i = 0; i < size; i++) {
        char str[5];
        sprintf(str, "%d", i);
        jstring jStr = env->NewStringUTF(str);
        env->SetObjectArrayElement(jarr, i, jStr);
    }
    // 返回数组
    return jarr;
}
