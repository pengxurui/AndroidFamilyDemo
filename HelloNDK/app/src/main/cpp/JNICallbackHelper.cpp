//
// Created by 彭旭锐 on 11/7/2022.
//

#include "JNICallbackHelper.h"

JNICallbackHelper::JNICallbackHelper(JavaVM *vm, JNIEnv *env, jobject job) {
    // 全局共享的 JavaVM*
    this->vm = vm;
    // 主线程的 JNIEnv*
    this->env = env;

    // C 回调 Java
    jclass mediaPlayerKTClass = env->GetObjectClass(job);
    jmd_prepared = env->GetMethodID(mediaPlayerKTClass, "onPrepared", "()V");

    // jobject 无法跨越线程，需要转换为全局引用
    // this->job = job;
    this->job = env->NewGlobalRef(job);
}

JNICallbackHelper::~JNICallbackHelper() {
    vm = nullptr;
    // 释放全局引用
    env->DeleteGlobalRef(job);
    job = nullptr;
    env = nullptr;
}

void JNICallbackHelper::onPrepared(int thread_mode) {
    if (thread_mode == THREAD_MAIN) {
        // 主线程：直接使用持有的主线程 env
        env->CallVoidMethod(job, jmd_prepared);
    } else if (thread_mode == THREAD_CHILD) {
        // 子线程：不能直接使用持有的主线程 env，需要通过 AttachCurrentThread 获取子线程的 env
        JNIEnv * env_child;
        vm->AttachCurrentThread(&env_child, nullptr);
        env_child->CallVoidMethod(job, jmd_prepared);
        vm->DetachCurrentThread();
    }
}



