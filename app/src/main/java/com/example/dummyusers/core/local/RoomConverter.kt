package com.example.dummyusers.core.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomConverter {
    @TypeConverter
    fun fromUserLocationData(data: UserLocationData?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun toUserLocationData(data: String?): UserLocationData? {
        return Gson().fromJson(data, UserLocationData::class.java)
    }
}
