package com.example.android.unittestingapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.databinding.RepoItemBinding

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var repos = ArrayList<Repo>()
    lateinit var onRepoClickListener: OnRepoClickListener

    inner class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(repo: Repo) {
            binding.repoName.text = repo.repoName
            binding.repoDesc.text = repo.repoDesc

            binding.root.setOnClickListener {
                onRepoClickListener.onClick()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(repos: List<Repo>) {
        this.repos = repos as ArrayList<Repo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
        return RepoViewHolder(RepoItemBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(this.repos[position])
    }

    override fun getItemCount(): Int = this.repos.size
}

interface OnRepoClickListener {
    fun onClick()
}