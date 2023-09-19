package com.example.githubusers.ui.Detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFollowBinding
import com.example.githubusers.ui.Main.GitUsersAdapter

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: GitUsersAdapter
    private lateinit var username: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = GitUsersAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager= LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }


        viewModel = ViewModelProvider(requireActivity()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)

        viewModel.getListFollowers().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                adapter.submitList(it)
                showLoading(false)
            }
        })


        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}