package com.example.githubusers.ui.fav

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import com.example.githubusers.data.local.FavUser
import com.example.githubusers.databinding.ItemUserBinding
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.R
import com.example.githubusers.ui.detail.UserDetailActivity


class FavUserAdapter() : ListAdapter<FavUser, FavUserAdapter.FavViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val favuser = getItem(position)
        holder.bind(favuser)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intentDetail = Intent(context,UserDetailActivity::class.java)
            intentDetail.putExtra(UserDetailActivity.EXTRA_USERNAME,favuser.username)
            intentDetail.putExtra(UserDetailActivity.EXTRA_URL,favuser.urlAvatar)

            context.startActivity(intentDetail)
        }
    }

   class FavViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root) {
        fun bind(favUser: FavUser) {
            binding.tvItemName.text="${favUser.username}"
            Glide.with(itemView.context)
                .load(favUser.urlAvatar)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .into(binding.imgItemPhoto)
        }
   }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavUser>() {
                override fun areItemsTheSame(oldItem: FavUser, newItem: FavUser): Boolean {
                    return oldItem == newItem
                }
                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FavUser, newItem: FavUser): Boolean {
                    return oldItem == newItem
                }
        }
    }
}