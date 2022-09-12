package com.example.android.unittestingapp.data.source.network

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.source.RepoDataSource
import com.example.android.unittestingapp.data.source.network.responses.CommitNetworkMapper
import com.example.android.unittestingapp.data.source.network.responses.RepoNetworkMapper
import com.example.android.unittestingapp.data.util.Result
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(private val api: GithubApi) :
    RepoDataSource {
    override suspend fun getReposForUser(username: String): Result<List<Repo>> {
        return try {
            val repoNetworkEntity = api.getReposForUser(username)
            val repos = RepoNetworkMapper.mapFromEntityList(repoNetworkEntity)
            Result.Success(repos)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCommits(username: String, repoTitle: String): Result<List<Commit>> {
        return try {
            val commitEntities = api.getCommits(username, repoTitle)
            val commits = CommitNetworkMapper.mapFromEntityList(commitEntities)
            Result.Success(commits)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getReposForUser(): Result<List<Repo>> {
        return Result.Success(emptyList())
    }

    override suspend fun insertAllRepos(repos: List<Repo>) {
        return
    }

    override suspend fun deleteAllRepos() {
        return
    }

}