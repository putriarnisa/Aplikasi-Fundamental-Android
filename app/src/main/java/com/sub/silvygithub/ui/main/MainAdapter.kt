package com.sub.silvygithub.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.databinding.ItemUserBinding

class MainAdapter(private val getListUser: List<UserItems>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return getListUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getUser = getListUser[position]
        with(holder.binding) {
            tvItemUsername.text = getUser.login
            Glide.with(root.context).load(getUser.avatarUrl).into(imgItemProfile)
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(getUser)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItems)
    }
}