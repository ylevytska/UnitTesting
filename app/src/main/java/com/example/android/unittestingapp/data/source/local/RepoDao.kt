package com.example.android.unittestingapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {
    @Query("SELECT * FROM cashedRepos")
    suspend fun getRepos(): List<RepoDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepos(repos: List<RepoDatabaseEntity>)

    @Query("DELETE FROM cashedRepos")
    suspend fun deleteAllRepos()

}