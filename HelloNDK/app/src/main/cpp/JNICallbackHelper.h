//
// Created by 彭旭锐 on 11/7/2022.
//

#ifndef HELLONDK_JNICALLBACKHELPER_H
#define HELLONDK_JNICALLBACKHELPER_H

#include <jni.h>
#include "util.h"

class JNICallbackHelper {

private:
    // 全局共享的 JavaVM*
    JavaVM *vm = 0;
    // 主线程的 JNIEnv*
    JNIEnv *env = 0;
    // Java 层的对象 MediaPlayer.kt
    jobject job;
    // Java 层的方法 MediaPlayer#onPrepared()
    jmethodID jmd_prepared;

public:
    JNICallbackHelper(JavaVM *vm, JNIEnv *env, jobject job);

    ~JNICallbackHelper();

    void onPrepared(int thread_mode);
};

#endif //HELLONDK_JNICALLBACKHELPER_H
