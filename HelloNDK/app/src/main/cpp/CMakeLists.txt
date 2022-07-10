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
add_subdirectory(${CMAKE_SOURCE_DIR}/libsubc)
add_subdirectory(${CMAKE_SOURCE_DIR}/libsubcpp)

# 引入 FFMPEG 头文件
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include)
# 引入 FFMPEG 库文件
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/ffmpeg/armeabi-v7a")

# 链接
target_link_libraries(
        hellondk
        log
        sub1
        sub2
        -Wl,--start-group # 忽略顺序引发的问题
        avformat avcodec avfilter avutil swresample swscale
        -Wl,--end-group
        )