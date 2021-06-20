package com.xurui.ktx.property

import kotlin.reflect.KProperty

/**
 * Created by pengxr on 2021/5/26.
 */

class MapAccessors(val map: MutableMap<String, Any?>) {

    /**
     * Returns the value of the property for the given object from this mutable map.
     * @param thisRef the object for which the value is requested (not used).
     * @param property the metadata for the property, used to get the name of property and lookup the value corresponding to this name in the map.
     * @return the property value.
     */
    @kotlin.jvm.JvmName("getVar")
    public inline operator fun <V> getValue(thisRef: Any?, property: KProperty<*>): V = @Suppress("UNCHECKED_CAST") (map[property.name] as V)

    /**
     * Stores the value of the property for the given object in this mutable map.
     * @param thisRef the object for which the value is requested (not used).
     * @param property the metadata for the property, used to get the name of property and store the value associated with that name in the map.
     * @param value the value to set.
     */
    public inline operator fun <V> setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        map[property.name] = value
    }
}