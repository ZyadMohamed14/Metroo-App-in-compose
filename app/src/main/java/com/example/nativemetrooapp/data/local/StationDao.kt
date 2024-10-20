package com.example.nativemetrooapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nativemetrooapp.domain.Station
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertStation(station: Station)

    @Query("SELECT * FROM stations")
     fun getAllStations(): Flow<List<Station>>
    @Delete
     fun deleteStation(station: Station)


}