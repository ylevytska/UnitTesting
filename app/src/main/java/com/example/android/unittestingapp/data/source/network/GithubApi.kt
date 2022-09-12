package com.example.android.unittestingapp.data.source.network

import com.example.android.unittestingapp.data.source.network.responses.CommitNetworkEntity
import com.example.android.unittestingapp.data.source.network.responses.ReposNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}/repos")
    suspend fun getReposForUser(
        @Path("username") username: String,
    ): List<ReposNetworkEntity>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") username: String,
        @Path("repo") repo: String,
    ): List<CommitNetworkEntity>
}