package com.example.nativemetrooapp.presentation.metroo

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativemetrooapp.domain.AppResources
import com.example.nativemetrooapp.helper.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationHelper: LocationHelper
) : ViewModel() {


    private val _originStation = mutableStateOf("")
    val originStation: State<String> = _originStation

    private val _destinationStation = mutableStateOf("")
    val destinationStation: State<String> = _destinationStation

    fun setOriginStation(newStation: String) {
        _originStation.value = newStation
    }

    fun setDestinationStation(newStation: String) {
        _destinationStation.value = newStation
    }

    init {
        Log.d("LifecycleViewModel", "ViewModel initialized")
    }

    override fun onCleared() {
        Log.d("LifecycleViewModel", "ViewModel cleared")
        super.onCleared()
    }
//    private val _currentLocation = MutableStateFlow<Location?>(null)
//    val currentLocation: StateFlow<Location?> = _currentLocation

    private val _currentLocation = MutableStateFlow<Location?>(null) // Initialized as null
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()

    fun fetchCurrentLocation() {
        Log.d("LocationViewModel", "Fetching current location")
        viewModelScope.launch {
            try {
                val location = locationHelper.getCurrentLocation()
                _currentLocation.value = location
                Log.d("LocationViewModel", "Current location fetched: $location")
            } catch (e: Exception) {
                // Handle exception (e.g., log it, notify user)
                _currentLocation.value = null // or you can set a specific error state
                Log.e("LocationViewModel", "Error fetching current location", e)
            }
        }
    }
    private val _location = MutableStateFlow<AppResources<Pair<Double, Double>>>(AppResources.Idle()) // Initialize as Idle
    val location: StateFlow<AppResources<Pair<Double, Double>>> = _location
     fun getLatLngFromAddress( address:String) {
        viewModelScope.launch {
            _location.value = AppResources.Loading() // Set loading state
            val fetchedAddress = locationHelper.getLatLngFromAddress(address)
            if (fetchedAddress != null) {
                _location.value = AppResources.Success(fetchedAddress) // Set success state
            } else {
                _location.value = AppResources.Error("No address found") // Set error state
            }
        }
    }
}