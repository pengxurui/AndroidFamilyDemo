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
