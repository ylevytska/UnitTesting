package com.example.android.unittestingapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.databinding.CommitItemBinding

class CommitAdapter : RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    private var commits = ArrayList<Commit>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(commits: List<Commit>) {
        this.commits = commits as ArrayList
        notifyDataSetChanged()
    }

    inner class CommitViewHolder(private val binding: CommitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(commit: Commit) {
            binding.authorName.text = commit.author.name
            binding.commitMessage.text = commit.message
            binding.commitDate.text = commit.author.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val view = LayoutInflater.from(parent.context)
        return CommitViewHolder(CommitItemBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        holder.onBind(commits[position])
    }

    override fun getItemCount(): Int = commits.size
}