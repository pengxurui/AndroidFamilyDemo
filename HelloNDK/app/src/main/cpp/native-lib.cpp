#include <jni.h>
#include <string>
#include <android/log.h>

// libsubc 是 C 语言实现的，需要转为 C 语言的编译方式
extern "C" {
#include "libsubc/sub1.h"
}

#include "libsubcpp/sub2.h"

#define TAG "XIAOPENG"
// __VA_ARGS__ 代表 ...的可变参数
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG,  __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG,  __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG,  __VA_ARGS__);

extern "C" JNIEXPORT jstring JNICALL
Java_com_pengxr_hellondk_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    // get信息的输出显示
    LOGD("hello:%s", helloSub1());
    LOGD("hello:%s", helloSub2());

    return env->NewStringUTF(hello.c_str());
}