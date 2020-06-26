package com.musclegamez.fitness_app.ui.data.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by root on 11/05/20.
 */
object SharedPreferencesHelper {

    private const val NAME = "Musclegamez"
    private const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun setPrefValue(key: String, value: Any?) {
        when (value) {
            is String? -> preferences.edit { it.putString(key, value) }
            is Int -> preferences.edit { it.putInt(key, value) }
            is Boolean -> preferences.edit { it.putBoolean(key, value) }
            is Float -> preferences.edit { it.putFloat(key, value) }
            is Long -> preferences.edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    inline fun <reified T : Any> getPrefValue(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> preferences.getString(key, defaultValue as? String) as T?
            Int::class -> preferences.getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> preferences.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> preferences.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> preferences.getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun onClear()
    {
        preferences.edit().clear().apply()
    }

}