package ir.alirezaivaz.tablericons.demo.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager

class Preferences(context: Context) {
    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        editor = prefs?.edit()
    }

    fun getDynamicColors(): Boolean {
        return get(PREF_DYNAMIC_COLORS, PREF_DYNAMIC_COLORS_DEFAULT)
    }

    fun setDynamicColors(value: Boolean) {
        put(PREF_DYNAMIC_COLORS, value)
    }

    fun getTheme(): Int {
        val defaultValue = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }

            else -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        }
        return get(PREF_THEME, defaultValue)
    }

    fun setTheme(value: Int) {
        put(PREF_THEME, value)
    }

    fun getLanguage(): String {
        return AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: PREF_LANGUAGE_DEFAULT
    }

    fun setLanguage(value: String) {
        val localeList = LocaleListCompat.forLanguageTags(value)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    fun get(key: String, defValue: Int): Int {
        return prefs?.getInt(key, defValue) ?: defValue
    }

    fun get(key: String, defValue: Long): Long {
        return prefs?.getLong(key, defValue) ?: defValue
    }

    fun get(key: String, defValue: Float): Float {
        return prefs?.getFloat(key, defValue) ?: defValue
    }

    fun get(key: String, defValue: String): String {
        return prefs?.getString(key, defValue) ?: defValue
    }

    fun get(key: String, defValue: Boolean): Boolean {
        return prefs?.getBoolean(key, defValue) ?: defValue
    }

    fun put(key: String, value: Int) {
        editor?.putInt(key, value)?.apply()
    }

    fun put(key: String, value: Long) {
        editor?.putLong(key, value)?.apply()
    }

    fun put(key: String, value: Float) {
        editor?.putFloat(key, value)?.apply()
    }

    fun put(key: String, value: String) {
        editor?.putString(key, value)?.apply()
    }

    fun put(key: String, value: Boolean) {
        editor?.putBoolean(key, value)?.apply()
    }

    companion object {
        private var instance: Preferences? = null
        private const val PREF_DYNAMIC_COLORS = "pref_dynamic_colors"
        private const val PREF_DYNAMIC_COLORS_DEFAULT = true
        private const val PREF_THEME = "pref_theme"
        private const val PREF_LANGUAGE_DEFAULT = "en-US"

        fun getInstance(context: Context): Preferences {
            if (instance == null) {
                instance = Preferences(context)
            }
            return instance!!
        }
    }
}
