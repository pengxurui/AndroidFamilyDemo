**GCC 编译原生代码库示例**

GCC 是编译 C 语言代码的工具，需要注意：直接使用 GCC 编译器是本机编译，使用 NDK 内的 GCC 编译器才是针对 Android 平台的交叉编译。

- **本机编译：**

```jsx
// 生成 mainNative 可执行文件
gcc main.c -o mainNative 
// 执行可执行文件
./ mainNative 

//输出：
Hello GCC.
```

- **Android 交叉编译：**

```jsx
// 在 Android 手机上运行 mainNative，会报错：
not executable: 64-bit ELF file
```

现在命令行执行 `vi ~/.bash_profile` 修改系统环境配置，预先定义 NDK GCC 编译器需要的环境变量和参数：

`bash_profile`

```jsx
export NDK="/Users/pengxurui/Library/Android/sdk/ndk/17.2.4988734"
export PATH=${PATH}:${NDK}
# 四大ABI的交叉编译器
export NDK_GCC_X86=”${NDK}/toolchains/x86-4.9/prebuilt/darwin-x86_64/bin/i686-linux-android-gcc“
export NDK_GCC_X88_64="${NDK}/toolchains/x86_64-4.9/prebuilt/darwin-x86_64/bin/x86_64-linux-android-gcc"
export NDK_GCC_ARM="${NDK}/toolchains/arm-linux-androideabi-4.9/prebuilt/darwin-x86_64/bin/arm-linux-androideabi-gcc"
export NDK_GCC_ARM_64="${NDK}/toolchains/aarch64-linux-android-4.9/prebuilt/darwin-x86_64/bin/aarch64-linux-android-gcc"
# 库文件地址
export NDK_GCC_X86_ARG="--sysroot=${NDK}/platforms/android-21/arch-x86 -isystem ${NDK}/sysroot/usr/include -isystem ${NDK}/sysroot/usr/include/i686-linux-android"
export NDK_GCC_X86_64_ARG="--sysroot=${NDK}/platforms/android-21/arch-x86_64 -isystem ${NDK}/sysroot/usr/include -isystem ${NDK}/sysroot/usr/include/x86_64-linux-android"
export NDK_GCC_ARM_ARG="--sysroot=${NDK}/platforms/android-21/arch-arm -isystem ${NDK}/sysroot/usr/include -isystem ${NDK}/sysroot/usr/include/arm-linux-androideabi"
export NDK_GCC_ARM_64_ARG="--sysroot=${NDK}/platforms/android-21/arch-arm64 -isystem ${NDK}/sysroot/usr/include -isystem ${NDK}/sysroot/usr/include/aarch64-linux-android"
# 静态链接工具
export NDK_AR_X86="${NDK}/toolchains/x86-4.9/prebuilt/darwin-x86_64/bin/i686-linux-android-ar"
export NDK_AR_X86_64="${NDK}/toolchains/x86_64-4.9/prebuilt/darwin-x86_64/bin/x86_64-linux-android-ar"
export NDK_AR_ARM="${NDK}/toolchains/arm-linux-androideabi-4.9/prebuilt/darwin-x86_64/bin/arm-linux-androideabi-ar"
export NDK_AR_ARM_64="${NDK}/toolchains/aarch64-linux-android-4.9/prebuilt/darwin-x86_64/bin/aarch64-linux-android-ar"
```

最终命令：

```jsx
// 生成 mainARM64 可执行文件
$NDK_GCC_ARM_64 $NDK_GCC_ARM_64_ARG main.c -o mainARM64

// 查看文件信息
file mainARM64
// 输出
mainARM64: ELF 64-bit LSB executable, ARM aarch64, version 1 (SYSV), dynamically linked, interpreter /system/bin/linker64, not stripped

// adb 导入手机运行
adb push .\mainARM64 /sdcard
adb shell
cd sdcard/
./mainARM64
//输出：
Hello GCC.
```

- **编译为静态链接库：**

```jsx
// 本机编译
gcc -fPIC -shared main.c -o libmain.so
// 查看文件信息
file libmain.so
// 输出
libmain.so: Mach-O 64-bit dynamically linked shared library x86_64

// 交叉编译
$NDK_GCC_ARM_64 $NDK_GCC_ARM_64_ARG -fPIC -shared main.c -o libmainarm64.so
// 查看文件信息
file libmainarm64.so
// 输出
libmainarm64.so: ELF 64-bit LSB shared object, ARM aarch64, version 1 (SYSV), dynamically linked, not stripped
```

- **编译为动态链接库：**

```jsx
// 本机编译
gcc -fPIC -c main.c -o main.o
ar rcs -o libmain.a main.o

// 交叉编译
$NDK_GCC_ARM_64 $NDK_GCC_ARM_64_ARG -fPIC -c main.c -o mainARM64.o
$NDK_AR_ARM_64 rcs -o libmainarm64.a mainARM64.o

// 查看文件信息
file libmainarm64.a 
// 输出
libmainarm64.a: current ar archive
```