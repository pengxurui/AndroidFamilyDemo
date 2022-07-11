# 最低 CMake 版本
cmake_minimum_required(VERSION 3.18.1)

set(FFMPEG ${CMAKE_SOURCE_DIR}/ffmpeg)
# 导入 FFMPEG 的头文件
include_directories(${FFMPEG}/include)
# 导入 FFMPEG 库文件
set(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} -L${FFMPEG}/libs/armeabi-v7a")