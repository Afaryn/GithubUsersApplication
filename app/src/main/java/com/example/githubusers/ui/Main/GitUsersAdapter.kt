package com.example.githubusers.ui.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.response.ItemsItem
import com.example.githubusers.databinding.ItemUserBinding

class GitUsersAdapter :
    ListAdapter<ItemsItem, GitUsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: ((ItemsItem) -> Unit)? = null

    fun setOnItemClickCallback(callback: (ItemsItem) -> Unit) {
        onItemClickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = getItem(position)
                    onItemClickCallback?.invoke(user)
                }
            }
        }

        fun bind(user: ItemsItem) {
            binding.tvItemName.text = "@${user.login}"
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
