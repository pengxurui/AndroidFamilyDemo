package com.xurui.hellojni;

import android.util.Log;

/**
 * <p>
 * Created by pengxr on 5/5/2021
 */
public class HelloWorld {

    public static final int A = 1;

    private static String sName = "初始值";
    private String mName = "初始值";

    public static String getsName() {
        return sName;
    }

    public String getmName() {
        return mName;
    }

    private static void sHelloJava() {
        Log.d(MainActivity.Companion.getTAG(), "静态方法 helloJava");
    }

    private void helloJava() {
        Log.d(MainActivity.Companion.getTAG(), "实例方法 helloJava");
    }

    static {
        System.loadLibrary("Hello-World");
    }

    public native void sayHi();

    public native void accessField();

    public native void accessMethod();

}
