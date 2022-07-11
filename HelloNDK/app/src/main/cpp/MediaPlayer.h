//
// Created by 彭旭锐 on 11/7/2022.
//

#ifndef HELLONDK_MEDIAPLAYER_H
#define HELLONDK_MEDIAPLAYER_H

#include <cstring>
#include <pthread.h>
#include "AudioChannel.h"
#include "VideoChannel.h"
#include "JNICallbackHelper.h"

extern "C" {
#include <libavformat/avformat.h>
};

class MediaPlayer {
private:
    char *path = 0;
    JNICallbackHelper *helper = 0;
    pthread_t pid_prepare;
    AVFormatContext *formatContext = 0;
    AudioChannel *audio_channel = 0;
    VideoChannel *video_channel = 0;
public:
    MediaPlayer(const char *path, JNICallbackHelper *helper);

    ~MediaPlayer();

    // 打开媒体文件封装格式
    void openFile();

    void doOpenFile();

    void start();
};

#endif //HELLONDK_MEDIAPLAYER_H
