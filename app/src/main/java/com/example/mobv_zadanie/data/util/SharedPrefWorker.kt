package com.example.mobv_zadanie.data.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

//class for easy retrieving current user uid, access and refresh tokens (no need to create DB table for 1 row)
interface SharedPrefWorker {

    companion object {

        private var PRIVATE_MODE = 0
        private val PREF_NAME = "mobv_zadanie"


        fun saveString(appContext: Context, toSave: String, value: String){
            val sharedPref: SharedPreferences = appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

            if (sharedPref.getBoolean(PREF_NAME, false)) {
                Log.i("TAG_API", "unable to retrieve SHAREDPREF file")
            }
            else {
                val editor = sharedPref!!.edit()
                editor.putString(toSave, value)
                editor.apply()
            }
        }

        fun getString(appContext: Context, toRetrieve: String, defaultValue: String): String? {
            val sharedPref: SharedPreferences = appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            //second parameter is default value if object not found
            return sharedPref!!.getString(toRetrieve, defaultValue)
        }
    }
}