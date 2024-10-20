package com.example.nativemetrooapp.presentation.history

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativemetrooapp.data.local.StationDao
import com.example.nativemetrooapp.data.local.StationRepository
import com.example.nativemetrooapp.domain.Station
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class StationViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {
    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    val stations: StateFlow<List<Station>> = _stations.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun insertStation(station: Station) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("StationViewModel", "Inserting station: $station")
                stationRepository.insertStation(station)
                Log.d("StationViewModel", "Station inserted successfully")
            } catch (e: Exception) {
                Log.e("StationViewModel", "Error inserting station: ${e.message}")
            }
        }
    }
   suspend fun getAllStations() {
       stationRepository.getAllStations().collect { stationsList ->
           _stations.value = stationsList // Update the MutableStateFlow with the new list
           Log.d("StationViewModel", "Updated stations: $stationsList")
       }
   }


    // Delete a station
    fun deleteStation(station: Station) {
        viewModelScope.launch(Dispatchers.IO) {
            stationRepository.deleteStation(station)
        }
    }
}