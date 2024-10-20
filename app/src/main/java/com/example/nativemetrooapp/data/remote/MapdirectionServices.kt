package com.example.nativemetrooapp.data.remote

import androidx.viewbinding.BuildConfig
import com.example.nativemetrooapp.domain.DirectionsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapDirectionsService {

    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") originLatLng: String,
        @Query("destination") destinationLatLang: String,
        @Query("key") apiKey: String = "AIzaSyDdCje5iXRVMYbzIlufDIdRLhixAnHSwmY"
    ): Response<DirectionsDto>

}