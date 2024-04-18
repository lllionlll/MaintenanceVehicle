package io.maintenancevehicle.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    fun <T> setValue(key: String, value: T) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
        editor.apply()
    }

    inline fun <reified T> getValue(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> sharedPreferences.getString(key, defaultValue as String) as T
            Int::class -> sharedPreferences.getInt(key, defaultValue as Int) as T
            Long::class -> sharedPreferences.getLong(key, defaultValue as Long) as T
            Float::class -> sharedPreferences.getFloat(key, defaultValue as Float) as T
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }
}