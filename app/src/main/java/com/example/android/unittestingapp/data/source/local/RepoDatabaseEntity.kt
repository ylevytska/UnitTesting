package com.example.android.unittestingapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.unittestingapp.data.models.Owner

@Entity(tableName = "cashedRepos")
class RepoDatabaseEntity(
    @PrimaryKey val repoId: Long,
    val repoName: String,
    val repoDesc: String,
    val repoUrl: String,
    val repoOwner: Owner,
)