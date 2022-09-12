package com.example.android.unittestingapp.ui.commits

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.unittestingapp.databinding.FragmentCommitBinding
import com.example.android.unittestingapp.ui.adapters.CommitAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommitFragment : Fragment() {

    private lateinit var binding: FragmentCommitBinding
    private val commitViewModel: CommitViewModel by viewModels()
    private val commitAdapter = CommitAdapter()
    private val safeArgs: CommitFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = safeArgs.username
        val repoTitle = safeArgs.repoTitle
        commitViewModel.getCommitsForRepo(username, repoTitle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCommitBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoTxt.text = "Commits for ${safeArgs.repoTitle}"
        setUpBackBtn()
        setUpRecyclerView()
        observeData()
    }

    private fun setUpRecyclerView() {
        binding.commitsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commitAdapter
        }
    }

    private fun observeData() {
        commitViewModel.commits.observe(viewLifecycleOwner) { commits ->
            if (commits.isEmpty()) {
                commitAdapter.setData(ArrayList())
                binding.noCommitsFoundTxt.visibility = View.VISIBLE
            } else {
                binding.noCommitsFoundTxt.visibility = View.INVISIBLE
                commitAdapter.setData(commits)
            }
        }

        commitViewModel.error.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        commitViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            binding.prBar.visibility = if (showLoading) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setUpBackBtn() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}