[![License][licenseSvg]][license]

## [中文说明文档][readme-cn]

## About Kotlin Delegate

Kotlin Delegate is a feature of Kotlin, which is used to implement delegate pattern more gracefully. In fact, the "by" syntax is some kind of essentially compiler oriented syntax sugar. Three kinds of delegate (class delegate, object delegate and local variable delegate) will be converted to "sugar free syntax" at compile time. For example, class delegation: the compiler will implements all methods of the base interface and delegates them directly to the base object. For another example, object delegate and local variable delegate: the compiler will generate the auxiliary property (prop $degelate), while the getter() and setter() methods of the property / variable are simply delegated to the getvalue() and setvalue() methods of the auxiliary property.

By the way, If you don't know much about Kotlin delegation, this article will help you get started：[《Kotlin | 委托机制 & 原理 & 应用》](https://juejin.cn/post/6958346113552220173)。

## About AKotlinDelegates

[AKotlinDelegates](https://github.com/pengxurui/DemoHall/tree/main/AKotlinDelegates)🔥 is an easy-to-use Android Kotlin delegate tool. It encapsulates a series of kotlin delegation properties for Android development scenarios. Using its APIs can improve the development efficiency. At present, the implemented scenarios are as follows:

#### ✅ Fragment / Activity Arguments

Usually, we need to pass parameters between activities or fragments. However, we need to write a lot of template code, suah as:

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

Using AKotlinDelegate, you only need to write a few tempalte code, such as:

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

Inside Activity:

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
#### ✅ ViewBinding

Viewbinding is a new feature in Android gradle plug 3.6, which is used to implement view binding more lightweight. It can be understood as a lightweight version of databinding. The usage and implementation principle of viewbinding are well understood, but the conventional usage has some limitations, such as:

- **1、Creating and recycling viewbinding objects requires repeating the template code, especially the cases used in fragment;**
- **2、The binding property is nullable and variable, which is inconvenient to use.**

Using kotlin attribute delegation can solve these two problems gracefully. You can easily use viewbinding in the following scenarios without worrying about memory leakage caused by life cycle changes:

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

## Contract me

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

[readme-cn]: https://github.com/pengxurui/DemoHall/blob/main/KotlinDelegate/README-CN.md

[juejinSvg]: https://img.shields.io/badge/%E6%8E%98%E9%87%91-%40%E5%BD%AD%E4%B8%91%E4%B8%91-blue
[juejin]: https://juejin.cn/user/1063982987230392

[jianshuSvg]: https://img.shields.io/badge/%E7%AE%80%E4%B9%A6-%40%E5%BD%AD%E4%B8%91%E4%B8%91-blue
[jianshu]: https://www.jianshu.com/u/d9761b0d1618

[wechatSvg]: https://img.shields.io/badge/WeChat-%40%E5%BD%AD%E6%97%AD%E9%94%90-blue
[wechat]: https://github.com/pengxurui/DemoHall/blob/main/images/wechat.jpeg

[wechatPublicSvg]: https://img.shields.io/badge/WeChat%20Public-%40%E5%BD%AD%E6%97%AD%E9%94%90-blue
[wechatPublic]: https://github.com/pengxurui/DemoHall/blob/main/images/wechat_public.jpg
