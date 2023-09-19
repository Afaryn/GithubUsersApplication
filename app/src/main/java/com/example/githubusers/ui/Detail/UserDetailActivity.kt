package com.example.githubusers.ui.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubusers.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME="extra_username"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)

        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this) {
                if (it != null) {
                    binding.apply {
                        tvName.text = it.name
                        tvUsername.text = "@${it.login}"
                        tvFollower.text = "${it.followers} Followers"
                        tvFollowing.text = "${it.following} Following"
                        Glide.with(this@UserDetailActivity)
                            .load(it.avatarUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(ivPhoto)
                    }
                }
            }
        val PagerAdapter = PagerAdapter(this,supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = PagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}