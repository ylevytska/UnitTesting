package com.example.android.unittestingapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RepoDatabaseEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun getRepoDao(): RepoDao
}