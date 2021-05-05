package com.xurui.hellojni;

/**
 * <p>
 * Created by pengxr on 5/5/2021
 */
public class HelloWorld {

    static{
        System.loadLibrary("Hello-World");
    }

    public static final int A = 1;

    public native void sayHi();

}
