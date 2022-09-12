package com.example.android.unittestingapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.unittestingapp.databinding.ActivityMainBinding
import com.example.android.unittestingapp.ui.adapters.OnRepoClickListener
import com.example.android.unittestingapp.ui.adapters.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repoAdapter = RepoAdapter()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()
        setUpRecyclerView()
        setUpShowRepoBtn()
        observeData()
    }

    private fun setUpAdapter(){
        repoAdapter.onRepoClickListener = object:OnRepoClickListener{
            override fun onClick() {

            }
        }
    }

    private fun setUpRecyclerView() {
        binding.reposRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }
    }

    private fun setUpShowRepoBtn() {
        binding.showReposBtn.setOnClickListener {
            if (correctUserInput()) {
                mainViewModel.getReposForUser(binding.searchUser.text.toString().trim(), true)
                binding.searchUser.error = null
            } else {
                binding.searchUser.error = "Required field"
            }
        }
    }

    private fun correctUserInput(): Boolean {
        val userInput = binding.searchUser.text.toString().trim()
        if (userInput.isEmpty()) {
            return false
        }
        return true
    }

    private fun observeData() {
        mainViewModel.repos.observe(this) { repos ->
            if (repos.isEmpty()) {
                repoAdapter.setData(ArrayList())
                binding.noReposFoundTxt.visibility = View.VISIBLE
            } else {
                binding.noReposFoundTxt.visibility = View.INVISIBLE
                repoAdapter.setData(repos)
            }
        }

        mainViewModel.error.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.loading.observe(this) { showLoading ->
            binding.prBar.visibility = if (showLoading) View.VISIBLE else View.INVISIBLE
        }
    }
}