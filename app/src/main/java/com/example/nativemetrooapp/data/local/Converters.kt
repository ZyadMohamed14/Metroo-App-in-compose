package com.example.nativemetrooapp.data.local

import androidx.room.TypeConverter

class Converters {

    // Convert a List<String> to a comma-separated String
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",") // Convert List<String> to a single String
    }

    // Convert a comma-separated String back to a List<String>
    @TypeConverter
    fun toStringList(value: String?): List<String?> {
        return value?.split(",") ?: emptyList() // Convert single String back to List<String>
    }
}