package com.example.githubusers.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.data.online.response.ItemsItem
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.detail.UserDetailActivity
import com.example.githubusers.ui.fav.FavUserActivity
import com.example.githubusers.ui.setting.ds
import com.example.githubusers.ui.setting.settingModeActivity
import com.example.githubusers.ui.setting.settingModeViewModel
import com.example.githubusers.ui.setting.settingPreferences
import com.example.githubusers.ui.setting.settingViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GitUsersAdapter()
        adapter.setOnItemClickCallback { user ->
            val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)
            startActivity(intent)
        }


        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        binding.rvUser.adapter = adapter // Set adapter to RecyclerView

        mainViewModel.listUser.observe(this) { listUser ->
            setUserData(listUser)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = searchView.text.toString().trim()
                        if (query.isNotEmpty()) {
                            mainViewModel.findUser(query)
                            searchBar.text = searchView.text
                        } else {
                            Toast.makeText(this@MainActivity, "Tolong Masukan Username", Toast.LENGTH_SHORT).show()
                        }
                        return@setOnEditorActionListener true
                    }

                    false
                }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val preferences = settingPreferences.getInstance(application.ds)
        val modeViewModel = ViewModelProvider(this,settingViewModelFactory(preferences)).get(
            settingModeViewModel::class.java
        )
        modeViewModel.getModeSettings().observe(this){isDarkModeActive:Boolean->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }

    private fun setUserData(userData: List<ItemsItem?>?) {
        if (userData != null) {
            adapter.submitList(userData)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_button -> {
                Intent(this, FavUserActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }

            R.id.darkmode -> {
                Intent(this, settingModeActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
