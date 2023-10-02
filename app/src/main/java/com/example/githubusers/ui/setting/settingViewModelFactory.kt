package com.example.githubusers.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class settingViewModelFactory(private val preferences: settingPreferences):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(settingModeViewModel::class.java)){
            return settingModeViewModel(preferences) as T
        }
        throw IllegalArgumentException("ViewModel tidak diketahui: "+modelClass.name)

    }
}