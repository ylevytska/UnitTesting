package com.example.android.unittestingapp.data.source

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.Result

interface RepoDataSource {
    suspend fun getReposForUser(username: String): Result<List<Repo>>

    suspend fun getCommits(username: String, repoTitle: String): Result<List<Commit>>

    suspend fun getReposForUser(): Result<List<Repo>>

    suspend fun insertAllRepos(repos: List<Repo>)

    suspend fun deleteAllRepos()
}