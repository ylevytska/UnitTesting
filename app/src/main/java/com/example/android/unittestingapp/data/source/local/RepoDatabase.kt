package com.example.android.unittestingapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoDatabaseEntity::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun getRepoDao(): RepoDao
}