package com.sub.silvygithub.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.databinding.FragmentDetailBinding
import com.sub.silvygithub.ui.detail.model.DetailViewModel
import com.sub.silvygithub.ui.detail.adapter.FollowAdapter
import com.sub.silvygithub.ui.detail.model.FollowViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvItemUser.addItemDecoration(itemDecoration)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        arguments?.getString(ARG_NAME)

        followViewModel.getFollower(
            activity?.intent?.getStringExtra(DetailViewModel.USERNAME).toString()
        )
        followViewModel.getFollowing(
            activity?.intent?.getStringExtra(DetailViewModel.USERNAME).toString()
        )
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        when (index) {
            1 -> followViewModel.getFollower.observe(viewLifecycleOwner)
            {setFollow(it) }
            2 -> followViewModel.getFollowing.observe(viewLifecycleOwner)
            { setFollow(it) }
        }
    }

    private fun setFollow(userFoll: List<UserItems>) {
        val adapter = FollowAdapter(userFoll)
        binding.rvItemUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }
}