package com.example.android.unittestingapp.data.models

data class Repo(
    val id: Long,
    val title: String,
    val desc: String,
    val url: String,
    val owner: Owner
)