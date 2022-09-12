package com.example.android.unittestingapp.data.repositories

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.source.RepoDataSource
import com.example.android.unittestingapp.data.util.Result
import javax.inject.Inject
import javax.inject.Named

class DefaultRepoRepository @Inject constructor(
    @field:Named("RemoteDataSource") private val repoRemoteDataSource: RepoDataSource,
    @field:Named("LocalDataSource") private val repoLocalDataSource: RepoDataSource,
) :
    RepoRepository {
    override suspend fun getReposForUser(
        username: String,
        forceUpdate: Boolean,
    ): Result<List<Repo>> {
        if (forceUpdate) {
            when (val networkResponse = repoRemoteDataSource.getReposForUser(username)) {
                is Result.Success -> {
                    repoLocalDataSource.deleteAllRepos()
                    repoLocalDataSource.insertAllRepos(networkResponse.data)
                }
                is Result.Error -> return networkResponse
            }
        }
        return repoLocalDataSource.getReposForUser()
    }

    override suspend fun getCommitsForRepo(
        username: String,
        repoTitle: String,
    ): Result<List<Commit>> {
        return repoRemoteDataSource.getCommits(username, repoTitle)
    }
}