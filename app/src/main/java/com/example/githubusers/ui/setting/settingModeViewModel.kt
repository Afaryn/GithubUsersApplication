package com.example.githubusers.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class settingModeViewModel(private val preferences: settingPreferences):ViewModel() {
    fun getModeSettings(): LiveData<Boolean>{
        return preferences.getModeSetting().asLiveData()
    }

    fun saveModeSettings(isDarkModeActive:Boolean){
        viewModelScope.launch {
            preferences.saveModeSetting(isDarkModeActive)
        }
    }
}