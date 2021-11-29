package com.app.pecodetest.data

import android.content.Context
import android.preference.PreferenceManager
import com.app.pecodetest.utils.Constants.FRAGMENTS_COUNT_KEY

class NotifRepository(private val context: Context) {

    fun saveFragmentsCount(count: Int) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val prefEditor = sharedPref.edit()
        prefEditor.putInt(FRAGMENTS_COUNT_KEY, count)
        prefEditor.apply()
    }

    fun readFragmentsCount(): Int {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getInt(FRAGMENTS_COUNT_KEY, 0)
    }

}