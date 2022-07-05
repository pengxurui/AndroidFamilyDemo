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

# 链接
target_link_libraries(
        hellondk
        log
        sub1
        sub2)