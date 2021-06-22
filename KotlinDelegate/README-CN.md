[![License][licenseSvg]][license]

## [README][readme]

## 一、关于 Kotlin 委托

委托（Delegate）是 Kotlin 的一种语言特性，用于更加优雅地实现委托模式。其实，Kotlin 委托的语法关键字是 by，其本质上是面向编译器的语法糖，三种委托（类委托、对象委托和局部变量委托）在编译时都会转化为 “无糖语法”。例如类委托：编译器会实现基础接口的所有方法，并直接委托给基础对象来处理。例如对象委托和局部变量委托：在编译时会生成辅助属性（prop$degelate），而属性 / 变量的 getter() 和 setter() 方法只是简单地委托给辅助属性的 getValue() 和 setValue() 处理。

对了，如果你还不太了解 Kotlin 委托机制，这篇文章会帮助你入门：[《Kotlin | 委托机制 & 原理 & 应用》](https://juejin.cn/post/6958346113552220173#heading-13)。

## 二、关于 KotlinDelegate

[KotlinDelegate](https://github.com/pengxurui/DemoHall/tree/main/KotlinDelegate)🔥是一个易用的安卓 Kotlin 委托工具类库，它针对安卓开发场景封装了一系列 Kotlin 委托属性，利用其 APIs 可以提高开发效率。目前，已经实现的场景有：

#### ✅ Kotlin 委托 + Fragment / Activity 传参

我们经常需要在 Activity / Fragment 之间传递参数，但往往需要编写很多样板代码，例如：

`OrderDetailFragment.kt`
```
class OrderDetailFragment : Fragment(R.layout.fragment_order_detail) {

    private var orderId: Int? = null
    private var orderType: Int? = null

    companion object {

        const val EXTRA_ORDER_ID = "orderId"
        const val EXTRA_ORDER_TYPE = "orderType";

        fun newInstance(orderId: Int, orderType: Int?) = OrderDetailFragment().apply {
            Bundle().apply {
                putInt(EXTRA_ORDER_ID, orderId)
                if (null != orderType) {
                    putInt(EXTRA_ORDER_TYPE, orderType)
                }
            }.also {
                arguments = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            orderId = it.getInt(EXTRA_ORDER_ID, 10000)
            orderType = it.getInt(EXTRA_ORDER_TYPE, 2)
        }
    }
}
```

而使用 KotlinDelegate，你只需要写很少的样板代码，例如：

`OrderDetailFragment.kt`
```
class OrderDetailFragment : Fragment(R.layout.fragment_order_detail) {

    private lateinit var tvDisplay: TextView

    private var orderId: Int by argument(10000)
    private var orderType: Int by argument(2)

    companion object {
        fun newInstance(orderId: Int, orderType: Int) = OrderDetailFragment().apply {
            this.orderId = orderId
            this.orderType = orderType
        }
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        // Display Value
        tvDisplay = root.findViewById(R.id.tv_display)
        tvDisplay.text = "orderId = $orderId, orderType = $orderType"
    }
}
```
类似地，也可以 Activity 中传参，例如:

```
fun Context.startOrderDetail(orderId: Int, orderType: Int?) {
    startActivity(Intent(this, OrderDetailActivity::class.java).apply {
        this["orderId"] = orderId
        this["orderType"] = orderType
    })
}

class OrderDetailActivity : AppCompatActivity() {

    private val orderId: Int by argument(1)
    private val orderType: Int? by argumentNullable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_order_detail)
    }
}
```

---
#### ✅ Kotlin 委托 + ViewBinding

ViewBinding 是 Android Gradle Plugin 3.6 中新增的特性，用于更加轻量地实现视图绑定，可以理解为轻量版本的 DataBinding。ViewBinding 的使用方法和实现原理都很好理解，但常规的使用方法存在一些局限性：

- **1、创建和回收 ViewBinding 对象需要重复编写样板代码，特别是在 Fragment 中使用的案例；**
- **2、binding 属性是可空的，也是可变的，使用起来不方便。**

使用 Kotlin 属性委托可以非常优雅地解决这两个问题，你可以在以下场景方便地使用 ViewBinding，并且不用担心生命周期变更造成的内存泄漏：

- **1、Activity**
```
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    ...
}
```
- **2、Fragment**
```
class OrderDetailFragment : Fragment(R.layout.fragment_order_detail) {

    private val binding by viewBinding(FragmentOrderDetailBinding::bind)
    ...
}
```
- **3、DialogFragment**
```
class OrderDetailDialogFragment : DialogFragment() {

    private val binding by viewBinding(DialogOrderDetailBinding::bind)
    ...
}
```
- **4、ViewGroup**
```
class OrderDetailView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val binding by viewBinding(LayoutOrderBinding::bind)
    ...
}
```
- **5、RecyclerView.ViewHolder**
```
class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(LayoutOrderBinding::bind)
    ...
}
```

## 三、联系我

[![掘金][juejinSvg]][juejin] [![简书][jianshuSvg]][jianshu] [![微信][wechatSvg]][wechat] [![公众号][wechatPublicSvg]][wechatPublic]

#### License
Copyright [2021] [Peng XuRui]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[licenseSvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/pengxurui/DemoHall/blob/main/LICENSE

[readme]: https://github.com/pengxurui/DemoHall/blob/main/KotlinDelegate/README.md

[juejinSvg]: https://img.shields.io/badge/%E6%8E%98%E9%87%91-%40%E5%BD%AD%E4%B8%91%E4%B8%91-blue
[juejin]: https://juejin.cn/user/1063982987230392

[jianshuSvg]: https://img.shields.io/badge/%E7%AE%80%E4%B9%A6-%40%E5%BD%AD%E4%B8%91%E4%B8%91-blue
[jianshu]: https://www.jianshu.com/u/d9761b0d1618

[wechatSvg]: https://img.shields.io/badge/%E5%BE%AE%E4%BF%A1-%40%E5%BD%AD%E6%97%AD%E9%94%90-blue
[wechat]: https://github.com/pengxurui/DemoHall/blob/main/images/wechat.jpeg

[wechatPublicSvg]: https://img.shields.io/badge/%E5%85%AC%E4%BC%97%E5%8F%B7-%40%E5%BD%AD%E6%97%AD%E9%94%90-blue
[wechatPublic]: https://github.com/pengxurui/DemoHall/blob/main/images/wechat_public.jpg
