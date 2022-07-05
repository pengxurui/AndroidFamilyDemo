# 最低 CMake 版本
cmake_minimum_required(VERSION 3.18.1)

# 批量导入源码
file(GLOB SOURCE *.cpp *.c)
add_library(
        sub1
        SHARED
        ${SOURCE})

