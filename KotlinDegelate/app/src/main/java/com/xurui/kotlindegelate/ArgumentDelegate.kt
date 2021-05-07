package com.xurui.kotlindegelate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by pengxr on 2021/4/7.
 */

fun <T> fragmentArgumentNullable(defaultValue: T? = null) = FragmentArgumentDelegateNullable(defaultValue)

fun <T> fragmentArgument() = FragmentArgumentDelegate<T>()

fun <T> activityArgumentNullable(defaultValue: T? = null) = ActivityArgumentDelegateNullable(defaultValue)

fun <T> activityArgument() = ActivityArgumentDelegate<T>()

// --------------------------------------------------------------------------------------
// Fragment
// --------------------------------------------------------------------------------------

class FragmentArgumentDelegate<T> : ReadWriteProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.getValue(property.name) as? T
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val arguments = thisRef.arguments ?: Bundle().also { thisRef.arguments = it }
        if (arguments.containsKey(property.name)) {
            // The Value is not expected to be modified
            return
        }
        arguments[property.name] = value
    }
}

class FragmentArgumentDelegateNullable<T>(private val defaultValue: T? = null) : ReadWriteProperty<Fragment, T?> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return thisRef.arguments?.getValue(property.name) ?: defaultValue
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        val arguments = thisRef.arguments ?: Bundle().also { thisRef.arguments = it }
        if (arguments.containsKey(property.name)) {
            // The Value is not expected to be modified
            return
        }
        arguments[property.name] = value
    }
}

// --------------------------------------------------------------------------------------
// Activity
// --------------------------------------------------------------------------------------

class ActivityArgumentDelegate<T> : ReadOnlyProperty<Activity, T> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return thisRef.intent?.extras?.getValue(property.name) as? T
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}

class ActivityArgumentDelegateNullable<T>(private val defaultValue: T? = null) :
    ReadOnlyProperty<Activity, T?> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T? {
        return thisRef.intent?.extras?.getValue(property.name) ?: defaultValue
    }
}

operator fun <T> Bundle.set(key: String, value: T?) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Short -> putShort(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is String? -> putString(key, value)
        is CharSequence? -> putCharSequence(key, value)
        is Serializable? -> putSerializable(key, value)
        is Parcelable? -> putParcelable(key, value)
        is Bundle? -> putBundle(key, value)
        is BooleanArray? -> putBooleanArray(key, value)
        is ByteArray? -> putByteArray(key, value)
        is CharArray? -> putCharArray(key, value)
        is ShortArray? -> putShortArray(key, value)
        is IntArray? -> putIntArray(key, value)
        is LongArray? -> putLongArray(key, value)
        is FloatArray? -> putFloatArray(key, value)
        is DoubleArray? -> putDoubleArray(key, value)
        is ArrayList<*>? -> throw IllegalStateException("ArrayList<*> $key is not supported")
        is Array<*>? -> throw IllegalStateException("Array<*> $key is not supported")
        else -> throw IllegalStateException("Type $key is not supported")
    }
}

fun <T> Bundle.getValue(key: String): T? {
    @Suppress("UNCHECKED_CAST")
    return get(key) as T?
}

operator fun <T> Intent.set(key: String, value: T) {
    when (value) {
        is Boolean -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Short -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is String? -> putExtra(key, value)
        is CharSequence? -> putExtra(key, value)
        is Serializable? -> putExtra(key, value)
        is Parcelable? -> putExtra(key, value)
        is Bundle? -> putExtra(key, value)
        is BooleanArray? -> putExtra(key, value)
        is ByteArray? -> putExtra(key, value)
        is CharArray? -> putExtra(key, value)
        is ShortArray? -> putExtra(key, value)
        is IntArray? -> putExtra(key, value)
        is LongArray? -> putExtra(key, value)
        is FloatArray? -> putExtra(key, value)
        is DoubleArray? -> putExtra(key, value)
        is ArrayList<*>? -> throw IllegalStateException("ArrayList<*> $key is not supported")
        is Array<*>? -> throw IllegalStateException("Array<*> $key is not supported")
        else -> throw IllegalStateException("Type $key is not supported")
    }
}