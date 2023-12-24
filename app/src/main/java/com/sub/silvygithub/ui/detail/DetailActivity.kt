package com.sub.silvygithub.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sub.silvygithub.R
import com.sub.silvygithub.data.remote.response.UserItemDetail
import com.sub.silvygithub.databinding.ActivityDetailBinding
import com.sub.silvygithub.ui.detail.model.DetailViewModel
import com.sub.silvygithub.ui.detail.adapter.FollowPagerAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var getUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        getUsername = intent.getStringExtra(DetailViewModel.USERNAME)
        getUsername?.let { username ->
            detailViewModel.getDetail(username)
        }

        detailViewModel.isLoading.observe(this) { isLoading -> showLoading(isLoading) }
        detailViewModel.getDetail.observe(this) { detailData -> getDetail(detailData) }

        val sectionsPagerAdapter = FollowPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabsLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun getDetail(detailData: UserItemDetail) {
        Glide.with(binding.root.context).load(detailData.avatarUrl).into(binding.imgProfile)
        binding.tvUsername.text = detailData.login
        binding.tvName.text = detailData.name
        binding.tvFollower.text = "${detailData.followers} Followers"
        binding.tvFollowing.text = "${detailData.following} Following"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}