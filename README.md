# DemoHall

## [中文说明文档][readme-cn]


## HelloJni

演示 JNI 机制

相关文章：

[NDK | 带你点亮 JNI 开发基石符文 (一)](https://juejin.cn/post/6973486697245835294)

---
## ✅ AKotlinDelegates

Kotlin Delegate is a feature of Kotlin, which is used to implement delegate pattern more gracefully. In fact, the "by" syntax is some kind of essentially compiler oriented syntax sugar. Three kinds of delegate (class delegate, object delegate and local variable delegate) will be converted to "sugar free syntax" at compile time. For example, class delegation: the compiler will implements all methods of the base interface and delegates them directly to the base object. For another example, object delegate and local variable delegate: the compiler will generate the auxiliary property (prop $degelate), while the getter() and setter() methods of the property / variable are simply delegated to the getvalue() and setvalue() methods of the auxiliary property.

[AKotlinDelegates](https://github.com/pengxurui/DemoHall/tree/main/KotlinDelegate) is an easy-to-use Android Kotlin delegate tool. It encapsulates a series of kotlin delegation properties for Android development scenarios. Using its APIs can improve the development efficiency.

By the way, If you don't know much about Kotlin delegation, these articles will help you get started：

[Kotlin | 委托机制 & 原理 & 应用](https://juejin.cn/post/6958346113552220173)

[Android | ViewBinding 与 Kotlin 委托双剑合璧](https://juejin.cn/post/6960914424865488932)

---
## MavenPublish

演示组件化发布

相关文章：

[Android工程化实践：组件化发布](https://juejin.cn/post/6963633839860088846)


---
## HelloAndroidX

演示 AndroidX 新组件

相关文章：

[Android | Jetpack 处理回退事件的新姿势 —— OnBackPressedDispatcher](https://juejin.cn/post/6967039557220958244)

----
#### 踩坑记录

1、升级到 Android Stidio 4.2 之后，如果在 Gradle 栏目中找不到 Task 列表，在设置里取消勾选此项即可：

![](https://upload-images.jianshu.io/upload_images/10107787-b037ae917d7fccac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2、构建报找不到包：Unable to resolve dependency for 'com.pengxr.demo:maven:v1.0.0’，可能是本地仓库中没有对应的类库。你需要先执行发布任务 uploadArchives。在 DemoHall 工程中，我声明了两个本地仓库：/snapshotRepository 和 /releaseRepository，分别对应快照版本和正式版本。更多细节可以阅读【MavenPublish Demo】的相关文章。


---
#### License
Copyright [2021] [pengxurui]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[readme-cn]: https://github.com/pengxurui/DemoHall/blob/main/README-CN.md
