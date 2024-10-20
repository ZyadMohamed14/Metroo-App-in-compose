package com.example.nativemetrooapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nativemetrooapp.domain.Station

@Database(entities = [Station::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class StationDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao

}
