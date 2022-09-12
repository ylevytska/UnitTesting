package com.example.android.unittestingapp.data.source

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.Result

class FakeDataSource(private var repos: MutableList<Repo>? = mutableListOf()) :
    RepoDataSource {
    override suspend fun getReposForUser(username: String): Result<List<Repo>> {
        repos?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Repos not found"))
    }

    override suspend fun getReposForUser(): Result<List<Repo>> {
        repos?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Repos not found"))
    }

    override suspend fun insertAllRepos(repos: List<Repo>) {
        this.repos?.addAll(repos)
    }

    override suspend fun deleteAllRepos() {
        repos?.clear()
    }
}