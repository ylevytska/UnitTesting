package com.example.android.unittestingapp.data.repositories

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.Result

interface RepoRepository {
    suspend fun getReposForUser(username: String, forceUpdate: Boolean): Result<List<Repo>>
    suspend fun getCommitsForRepo(username: String, repoTitle: String): Result<List<Commit>>
}