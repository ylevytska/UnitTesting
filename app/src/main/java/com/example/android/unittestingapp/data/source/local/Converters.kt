package com.example.android.unittestingapp.data.source.local

import androidx.room.TypeConverter
import com.example.android.unittestingapp.data.models.Owner
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromOwner(owner: Owner): String {
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun toOwner(stringOwner: String): Owner {
        return Gson().fromJson(stringOwner, Owner::class.java)
    }
}