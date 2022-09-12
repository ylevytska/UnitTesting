package com.example.android.unittestingapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.databinding.FragmentHomeBinding
import com.example.android.unittestingapp.ui.adapters.OnRepoClickListener
import com.example.android.unittestingapp.ui.adapters.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val repoAdapter = RepoAdapter()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpRecyclerView()
        setUpShowRepoBtn()
        observeData()
    }

    private fun setUpAdapter() {
        repoAdapter.onRepoClickListener = object : OnRepoClickListener {
            override fun onClick(repo: Repo) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCommitFragment(
                    repo.owner.login, repo.title
                ))
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
                homeViewModel.getReposForUser(binding.searchUser.text.toString().trim(), true)
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
        homeViewModel.repos.observe(viewLifecycleOwner) { repos ->
            if (repos.isEmpty()) {
                repoAdapter.setData(ArrayList())
                binding.noReposFoundTxt.visibility = View.VISIBLE
            } else {
                binding.noReposFoundTxt.visibility = View.INVISIBLE
                repoAdapter.setData(repos)
            }
        }

        homeViewModel.error.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            binding.prBar.visibility = if (showLoading) View.VISIBLE else View.INVISIBLE
        }
    }
}