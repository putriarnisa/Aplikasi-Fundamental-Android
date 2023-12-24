package com.sub.silvygithub.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.databinding.ItemUserBinding

class FollowAdapter(private val getFollow: List<UserItems>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = getFollow.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getFollow[position]
        holder.bind(user)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItems) {
            with(binding) {
                tvItemUsername.text = user.login
                Glide.with(root.context).load(user.avatarUrl).into(imgItemProfile)
            }
        }
    }
}