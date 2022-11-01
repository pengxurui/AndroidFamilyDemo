package com.pengxr.hellobuildvariant;

/**
 * 由于构建变体中定义了相同包名的 PayHelper，如果不注释会冲突。
 * <p>
 * Created by Peng Xurui on 1/11/2022
 */
//public class PayHelper {
//
//    public String func() {
//        // 编写条件语句
//        switch (BuildConfig.FLAVOR_tier) {
//            case "free":
//                return freeFunc();
//            case "paid":
//            default:
//                return paidFunc();
//        }
//    }
//
//    private String freeFunc() {
//        return "免费版";
//    }
//
//    private String paidFunc() {
//        return "付费版";
//    }
//}