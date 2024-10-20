package com.example.nativemetrooapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativemetrooapp.data.remote.MapsRepositoryImpl
import com.example.nativemetrooapp.domain.AppResources
import com.example.nativemetrooapp.domain.Route
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepositoryImpl
) :ViewModel() {
    private val _directions = MutableStateFlow<AppResources<Route>>(AppResources.Loading())
    val directions: StateFlow<AppResources<Route>> get() = _directions

    suspend fun fetchDirections(origin: LatLng, destination: LatLng) {
        val pathResult = mapsRepository.getDirections(
            origin = origin,
            destination = destination
        )

       _directions.value = pathResult
    }
}