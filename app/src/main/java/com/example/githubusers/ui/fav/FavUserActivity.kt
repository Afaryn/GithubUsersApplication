package com.example.githubusers.ui.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.data.online.response.GitResponse
import com.example.githubusers.databinding.ActivityFavUserBinding
import com.example.githubusers.ui.main.GitUsersAdapter

@Suppress("DEPRECATION")
class FavUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavUserBinding
    private lateinit var adapter: FavUserAdapter
    private lateinit var viewModel: FavViewModel

    private val favViewModel by viewModels<FavViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager=LinearLayoutManager(this@FavUserActivity)
            rvUser.adapter=adapter
        }

        favViewModel.getAllFavUser().observe(this) {favUser->
            adapter.submitList(favUser)
        }

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tindakan yang akan diambil saat tombol back ditekan
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}