# 最低 CMake 版本
cmake_minimum_required(VERSION 3.18.1)
# 项目名
project("hellondk")

# 批量导入源码
file(GLOB SOURCE *.cpp *.c)
add_library(
        hellondk
        SHARED
        ${SOURCE})

# 引入子项目
#add_subdirectory(${CMAKE_SOURCE_DIR}/ffmpeg)
# 引入 FFMPEG 头文件
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include)
# 引入 FFMPEG 库文件
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/ffmpeg/libs/armeabi-v7a")

# 链接
target_link_libraries(
        hellondk
        log
        -Wl,--start-group # 忽略顺序引发的问题
        avformat avcodec avfilter avutil swresample swscale
        -Wl,--end-group
        z # libz.so库，是FFmpeg需要用ndk的z库，FFMpeg需要额外支持  libz.so
#        rtmp # rtmp 后面会专门介绍 rtmp交叉编译+FFmpeg结合编译
        android # android 后面会专门介绍，目前先要明白的是 ANativeWindow 用来渲染画面的 == Surface对象
        OpenSLES # OpenSLES 后面会专门介绍，目前先要明白的是 OpenSLES 用来播放声音的-特别是在native层播放（最快的）
        )