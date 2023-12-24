package com.sub.silvygithub.ui.detail.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sub.silvygithub.ui.detail.DetailActivity
import com.sub.silvygithub.ui.detail.DetailFragment

class FollowPagerAdapter(activity: DetailActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}
