package com.example.githubusers.ui.setting

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.ds : DataStore<Preferences> by preferencesDataStore("setting")

class settingPreferences(private val ds:DataStore<Preferences>) {

    private val MODE_KEY = booleanPreferencesKey("mode_setting")

    fun getModeSetting():Flow<Boolean>{
        return ds.data.map { preferences->
            preferences[MODE_KEY]?:false
        }
    }

    suspend fun saveModeSetting(isDarkModeActive:Boolean){
        ds.edit { preferences->
            preferences[MODE_KEY]= isDarkModeActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: settingPreferences?=null

        fun getInstance(ds:DataStore<Preferences>):settingPreferences{
            return INSTANCE?: synchronized(this){
                val instance = settingPreferences(ds)
                INSTANCE =instance
                instance
            }
        }
    }
}