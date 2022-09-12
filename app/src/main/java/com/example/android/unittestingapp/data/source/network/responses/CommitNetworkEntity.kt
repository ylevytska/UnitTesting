package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Commit

data class CommitNetworkEntity(
    val commit: Commit,
    val sha: String,
    val url: String,
)