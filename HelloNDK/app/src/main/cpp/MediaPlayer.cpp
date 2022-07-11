//
// Created by 彭旭锐 on 11/7/2022.
//


#include "MediaPlayer.h"

MediaPlayer::MediaPlayer(const char *path, JNICallbackHelper *helper) {
    // error：参数 path 指向的空间被回收会造成悬空指针，应复制一份
    // this->path = path;
    this->path = new char[strlen(path) + 1];
    strcpy(this->path, path);

    this->helper = helper;
}

MediaPlayer::~MediaPlayer() {
    if (path) {
        delete path;
    }
    if (helper) {
        delete helper;
    }
}

// 在子线程执行
void MediaPlayer::doOpenFile() {
    // 新建全局上下文
    formatContext = avformat_alloc_context();
    // 打开媒体文件
    AVDictionary *dictionary = nullptr;
    av_dict_set(&dictionary, "timeout", "5000000", 0); // 微秒
    int result = avformat_open_input(&formatContext, this->path, nullptr, &dictionary);
    av_dict_free(&dictionary);

    if (result) {
        // 打开媒体文件失败
        return;
    }

    // 查找音视频流信息
    result = avformat_find_stream_info(formatContext, nullptr);
    if (result < 0) {
        return;
    }

    // 遍历流信息
    for (int i = 0; i < formatContext->nb_streams; ++i) {
        AVStream *stream = formatContext->streams[i];
        // 流中携带的解码器参数
        AVCodecParameters *parameters = stream->codecpar;
        // 流指定的解码器
        AVCodec *codec = avcodec_find_decoder(parameters->codec_id);
        // 新建解码器上下文
        AVCodecContext *codecContext = avcodec_alloc_context3(codec);
        if (!codecContext) {
            return;
        }
        // 将流携带的解码器参数复制到解码器上下文
        result = avcodec_parameters_to_context(codecContext, parameters);
        if (result < 0) {
            return;
        }
        // 打开解码器
        result = avcodec_open2(codecContext, codec, nullptr);
        if (result) {
            return;
        }
        if (parameters->codec_type == AVMediaType::AVMEDIA_TYPE_AUDIO) {
            // 音频流
            audio_channel = new AudioChannel();
        } else if (parameters->codec_type == AVMediaType::AVMEDIA_TYPE_VIDEO) {
            // 视频流
            video_channel = new VideoChannel();
        }
    } // end for

    if (!audio_channel && !video_channel) {
        return;
    }
    // 媒体文件打开成功
    helper->onPrepared(THREAD_CHILD);
}

// 在子线程执行
void *task_open(void *args) {
    // args 是 主线程DerryPlayer的实例的this变量
    auto *player = static_cast<MediaPlayer *>(args);
    player->doOpenFile();

    return nullptr;
}

// 在主线程执行
// 打开媒体文件封装格式
void MediaPlayer::openFile() {
    // 切换到子线程执行
    pthread_create(&pid_prepare, 0, task_open, this);
}

void MediaPlayer::start() {

}





