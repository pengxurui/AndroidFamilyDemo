# DemoHall

<p align='center'>
<a href="https://github.com/pengxurui/DemoHall" target="_blank"><img alt="GitHub" src="https://img.shields.io/github/stars/pengxurui/DemoHall?label=Stars&style=flat-square&logo=GitHub"></a>
</p>

<p align='center'>
<a href="https://www.github.com/pengxurui" target="_blank"><img src="https://img.shields.io/badge/作者-@彭旭锐-000000.svg?style=flat&logo=GitHub"></a>
<a href="https://github.com/pengxurui/Android-NoteBook/raw/master/images/搜一搜公众号.png" target="_blank"><img src="https://img.shields.io/badge/公众号-@彭旭锐-000000.svg?style=flat&logo=WeChat"></a>
<a href="https://juejin.cn/user/1063982987230392" target="_blank"><img src="https://img.shields.io/badge/掘金-@彭旭锐-000000.svg?style=flat&logo=JueJin"></a>
<a href="https://www.zhihu.com/people/pengxurui" target="_blank"><img src="https://img.shields.io/badge/知乎-@彭旭锐-000000.svg?style=flat&logo=Zhihu"></a>
</p>

![公众号](https://github.com/pengxurui/Android-NoteBook/raw/master/images/搜一搜公众号-文字-白色版.png)

## 使用方法

**1、先给一个 Star，你的支持对我非常重要**。我的内容质量绝对对得起你的 Star，给我一点创作的动力，感谢。

**2、加我微信，进小彭的 Android 交流群**。我们对群质量有要求，你可以在这里找到志同道合的朋友。群里可以讨论技术、分享文章、聊天、吐槽，允许适当发招聘广告，不受欢迎的行为是严格禁止的：

<p align='center'>
<img src="https://github.com/pengxurui/Android-NoteBook/raw/master/images/个人微信.jpeg" width = "200" />
</p>

**3、关注我的公众号 [彭旭锐]，坚持高质量原创内容，不人云亦云**，公众号后续是我主要的内容更新平台：

<p align='center'>
<img src="https://github.com/pengxurui/Android-NoteBook/raw/master/images/公众号.jpg" width = "230" />
</p>

**4、关注我的 [掘金](https://juejin.cn/user/1063982987230392)、[知乎](https://www.zhihu.com/people/pengxurui) 和 [Android 知识体系](https://github.com/pengxurui/Android-NoteBook)**，掘金上有我历史发布过的所有文章，Android-NoteBook 是我参考杜威十进制模型搭建的 Android 知识体系，你可以参考我的模型定制专属的知识体系。

# [中文说明文档][readme-cn]

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
## ✅ HelloTransform


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
# Donate

如果本仓库对你有帮助，可以请小彭喝杯速溶咖啡

<p align='center'>
<img src="https://github.com/pengxurui/Android-NoteBook/raw/master/images/微信收款码.jpeg" width = "200" />
</p>


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
