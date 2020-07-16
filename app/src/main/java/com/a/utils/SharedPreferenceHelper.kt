package com.a.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(mContext: Context) {

    companion object {
        lateinit var getInstance: SharedPreferenceHelper
    }

    private val mSharedPreferences: SharedPreferences = mContext.getSharedPreferences("A2020", Context.MODE_PRIVATE)

    operator fun set(key: String, value: String?) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    operator fun get(key: String): String? {
        return mSharedPreferences.getString(key, null)
    }

    fun setInt(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }


    fun setLong(key: String, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0L)
    }

    fun setBool(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBool(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun removeKey(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    fun clearSharePrefs() {
        mSharedPreferences.edit().clear().apply()
    }
}