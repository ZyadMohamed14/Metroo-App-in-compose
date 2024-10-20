package com.example.nativemetrooapp.data.remote

import android.util.Log
import com.example.nativemetrooapp.domain.AppResources
import com.example.nativemetrooapp.domain.Route
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val directionsService: MapDirectionsService
)  {
     suspend fun getDirections(origin: LatLng, destination: LatLng): AppResources<Route> {
        return try {

            val response = directionsService.getDirections(
                originLatLng = "${origin.latitude},${origin.longitude}",
                destinationLatLang = "${destination.latitude},${destination.longitude}",
            )


            if (response.isSuccessful && response.body() != null){

                val polyLinePoints = try {
                    response.body()!!.routes[0].legs[0].steps.map { step ->
                        step.polyline.decodePolyline(step.polyline.points)
                    }
                }catch (e: Exception){ emptyList() }
                AppResources.Success(data = Route(routePoints = polyLinePoints))
            }else{
                AppResources.Error(response.message())
            }

        }catch (e:Exception){
            Log.d("TsdsadsadsadasdsadsaAG", "getDirections: ${e.message}")
            AppResources.Error("Something went wrong")
        }
    }
}