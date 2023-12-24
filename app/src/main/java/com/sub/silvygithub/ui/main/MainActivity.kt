package com.sub.silvygithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.databinding.ActivityMainBinding
import com.sub.silvygithub.ui.detail.DetailActivity
import com.sub.silvygithub.ui.detail.model.DetailViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvItemUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItemUser.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val textSearchBar = searchView.text
                searchView.hide()
                mainViewModel.getUserSearch(textSearchBar.toString())
                false
            }
        }

        mainViewModel.getUser()
        mainViewModel.getUser.observe(this) { setUser(it) }
        mainViewModel.getUserSearch.observe(this) { dataUser -> setUser(dataUser) }
        mainViewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setUser(githubUser: List<UserItems>) {
        val adapter = MainAdapter(githubUser)
        binding.rvItemUser.adapter = adapter

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                sendUser(data)
            }
        })

        if (adapter.itemCount < 1) Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show().also { mainViewModel.getUser.observe(this) { setUser(it) } }
    }

    private fun sendUser(data: UserItems) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailViewModel.USERNAME, data.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}