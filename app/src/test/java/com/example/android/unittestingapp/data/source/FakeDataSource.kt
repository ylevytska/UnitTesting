package com.example.android.unittestingapp.data.source

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.Result

class FakeDataSource(
    private var repos: MutableList<Repo>? = mutableListOf(),
    private var commits: MutableList<Commit>? = mutableListOf(),
) :
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

    override suspend fun getCommits(username: String, repoTitle: String): Result<List<Commit>> {
        commits?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Commits not found"))
    }

    override suspend fun insertAllRepos(repos: List<Repo>) {
        this.repos?.addAll(repos)
    }

    override suspend fun deleteAllRepos() {
        repos?.clear()
    }
}