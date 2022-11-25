package uz.mamadaliev.simplewebview

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefrenceHelper(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFS_NAME", MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    fun setLastUrl(lastUrl: String) {
        editor = preferences.edit()
        editor.putString("LASTURL", lastUrl)
        editor.apply()
    }

    fun getLastUrl() = preferences.getString("LASTURL", "https://www.google.com/")
}
