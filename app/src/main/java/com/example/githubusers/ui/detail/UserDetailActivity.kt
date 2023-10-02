package com.example.githubusers.ui.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubusers.R
import com.example.githubusers.data.local.FavUser
import com.example.githubusers.databinding.ActivityUserDetailBinding
import com.example.githubusers.ui.fav.FavViewModel
import com.example.githubusers.ui.fav.ViewModelFactory


@Suppress("DEPRECATION")
class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME="extra_username"
        const val EXTRA_URL = "extra_url"

    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: DetailUserViewModel
    private val favViewModel by viewModels<FavViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    private var favUser = FavUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

                        favUser.username=it.login!!
                        favUser.urlAvatar=it.avatarUrl

                    }
                }
            }
            favViewModel.getFavUserByUsername(username).observe(this){
                if (it!=null){
                    binding.fabFav.setImageResource(R.drawable.favorite_full_ic)
                    binding.fabFav.setOnClickListener{
                        favViewModel.deleteFavUser(favUser)
                    }
                }else{
                    binding.fabFav.setImageResource(R.drawable.favorite_border_ic)
                    binding.fabFav.setOnClickListener{
                        favViewModel.insertFavuser(favUser)
                    }
                }
            }

            val pagerAdapter = PagerAdapter(this,supportFragmentManager,bundle)
            binding.apply {
                viewPager.adapter = pagerAdapter
                tabs.setupWithViewPager(viewPager)
            }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
