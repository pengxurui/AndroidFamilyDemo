# DemoHall



## HelloJni

演示 JNI 机制

相关文章：

[NDK | 带你点亮 JNI 开发基石符文 (一)](https://juejin.cn/post/6973486697245835294)

---
## ✅ AKotlinDelegates

委托（Delegate）是 Kotlin 的一种语言特性，用于更加优雅地实现委托模式。其实，Kotlin 委托的语法关键字是 by，其本质上是面向编译器的语法糖，三种委托（类委托、对象委托和局部变量委托）在编译时都会转化为 “无糖语法”。例如类委托：编译器会实现基础接口的所有方法，并直接委托给基础对象来处理。例如对象委托和局部变量委托：在编译时会生成辅助属性（prop$degelate），而属性 / 变量的 getter() 和 setter() 方法只是简单地委托给辅助属性的 getValue() 和 setValue() 处理。

[AKotlinDelegates](https://github.com/pengxurui/DemoHall/tree/main/KotlinDelegate) 是一个易用的安卓 Kotlin 委托工具类库，它针对安卓开发场景封装了一系列 Kotlin 委托属性，利用其 APIs 可以提高开发效率。

对了，如果你还不太了解 Kotlin 委托机制，这些文章会帮助你入门：

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

