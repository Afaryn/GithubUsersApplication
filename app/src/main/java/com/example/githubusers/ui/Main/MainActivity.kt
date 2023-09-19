package com.example.githubusers.ui.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.response.ItemsItem
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.Detail.UserDetailActivity


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

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

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
    }

    private fun setUserData(userData: List<ItemsItem?>?) {
        if (userData != null) {
            adapter.submitList(userData)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
