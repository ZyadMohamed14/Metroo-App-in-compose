package com.example.nativemetrooapp.data.local

import com.example.nativemetrooapp.domain.Station
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationRepository @Inject constructor(private val stationDao: StationDao) {

    // Insert a station
    suspend fun insertStation(station: Station) {
        stationDao.insertStation(station)
    }

    // Get all stations as a flow
    suspend fun getAllStations(): Flow<List<Station>> {
        return stationDao.getAllStations()
    }

    // Delete a station
    suspend fun deleteStation(station: Station) {
        stationDao.deleteStation(station)
    }
}