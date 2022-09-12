package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Owner
import com.google.gson.annotations.SerializedName

data class ReposNetworkEntity(
    @SerializedName("id")
    val repoId: Long,

    @SerializedName("name")
    val repoName: String,

    @SerializedName("description")
    val repoDesc: String?,

    @SerializedName("url")
    val repoUrl: String,

    @SerializedName("owner")
    val repoOwner: Owner
)