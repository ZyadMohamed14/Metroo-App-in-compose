package com.example.nativemetrooapp.domain

import com.google.android.gms.maps.model.LatLng

data class MapModel(
    val currentLocation: LatLng,
    val nearestStationLocation: LatLng,
    val currentLocationName: String?=null,
    val nearestStationName: String?=null,
    val destinationStation :String?=null
)