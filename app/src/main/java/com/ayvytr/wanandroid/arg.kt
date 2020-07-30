package com.ayvytr.wanandroid

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * 获取Fragment或者Activity附带参数。注意：根据[lazy]特性，当值为空时，[argNotNull]只有在使用到这个值的时候
 * 才会报空指针.
 * @author ayvytr
 */
inline fun <reified T> Fragment.argNotNull(key: String, defaultValue: T? = null) =
    lazy {
        getArgValue(key, defaultValue) ?: throw NullPointerException("key=$key：参数$key 不能为空")
    }

inline fun <reified T> Fragment.getArgValue(
    key: String,
    defaultValue: T?
): T? {
    val args = arguments!!
    val value = when (T::class.java) {
        String::class.java -> args.getString(key)
        Int::class.java -> args.getInt(key, defaultValue as Int)
        Float::class.java -> args.getFloat(key, defaultValue as Float)
        Boolean::class.java -> args.getBoolean(key, defaultValue as Boolean)
        Parcelable::class.java -> args.getParcelable(key) ?: defaultValue
        Serializable::class.java -> args.getSerializable(key) as Serializable
        else -> args.get(key) ?: defaultValue
    }
    return value as T?
}


inline fun <reified T> Fragment.arg(key: String, defaultValue: T? = null) =
    lazy {
        getArgValue(key, defaultValue)
    }

inline fun <reified T> Activity.argNotNull(key: String, defaultValue: T? = null) =
    lazy {
        getArgValue(key, defaultValue) ?: throw NullPointerException("key=$key：参数$key 不能为空")
    }

inline fun <reified T> Activity.arg(key: String, defaultValue: T? = null) =
    lazy {
        getArgValue(key, defaultValue)
    }

inline fun <reified T> Activity.getArgValue(key: String, defaultValue: T?): T? {
    val value = when (T::class.java) {
        String::class.java -> intent.getStringExtra(key)
        Int::class.java -> intent.getIntExtra(key, defaultValue as Int)
        Float::class.java -> intent.getFloatExtra(key, defaultValue as Float)
        Boolean::class.java -> intent.getBooleanExtra(key, defaultValue as Boolean)
        Parcelable::class.java -> intent.getParcelableExtra(key) ?: defaultValue
        Serializable::class.java -> intent.getSerializableExtra(key) as Serializable
        else -> intent.extras?.get(key) ?: defaultValue
    }
    return value as T?
}
