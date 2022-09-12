package com.example.android.unittestingapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashedRepos")
class RepoDatabaseEntity(
    @PrimaryKey val repoId: Long,
    val repoName: String,
    val repoDesc: String,
    val repoUrl: String,
)