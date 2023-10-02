package com.example.githubusers.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.R
import com.google.android.material.switchmaterial.SwitchMaterial

class settingModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_mode)

        val changeMode = findViewById<SwitchMaterial>(R.id.switch_theme)

        val preferences = settingPreferences.getInstance(application.ds)
        val viewModel=ViewModelProvider(this,settingViewModelFactory(preferences)).get(
            settingModeViewModel::class.java
        )

        viewModel.getModeSettings().observe(this){isDarkModeActive:Boolean->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                changeMode.isChecked=true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                changeMode.isChecked=false
            }
        }

        changeMode.setOnCheckedChangeListener{_:CompoundButton?,isCheked:Boolean->
            viewModel.saveModeSettings(isCheked)

        }
    }
}