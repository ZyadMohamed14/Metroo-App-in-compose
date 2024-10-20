package com.example.nativemetrooapp.helper

import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.nativemetrooapp.data.local.CairoLines
import com.example.nativemetrooapp.data.local.CairoLinesCoordinates
import com.example.nativemetrooapp.data.local.MetroApp
import com.example.nativemetrooapp.domain.NearstStation
import com.example.nativemetrooapp.domain.Station
import com.example.nativemetrooapp.presentation.metroo.LocationViewModel
import com.google.gson.Gson

class RouteHelper(
    private val locationViewModel: LocationViewModel,
    private val navController: NavController
) {
    val allRoutes = mutableListOf<Station>()
    val stationId="stationId"
    fun findMyRoute(
        origin: String,
        destination: String,
        context: Context, cairoLines: CairoLines
    ) {
        allRoutes.clear()
        val metroApp = MetroApp(
            start = origin,
            end = destination, context = context,
            cairoLines = cairoLines
        )

        if (metroApp.isValidData()) {
            metroApp.searchPath()
            val routes: List<List<String>> = metroApp.getRoutes()

            when (metroApp.getRoutes().size) {
                1 -> {
                    val station = Station(
                        path = routes[0],
                        direction = metroApp.getDirection()
                    )
                    allRoutes.add(station)
                    // Convert list of stations to JSON and navigate
                    val stationsJson = Gson().toJson(allRoutes) // Convert list of Stations to JSON
                    navController.navigate("station_details/$stationsJson")
                }

                2 -> {
                    val station1 = Station(
                        path = routes[0],
                        direction = metroApp.getDirectionForFirstRoute().toString()
                    ).apply {
                        transList.addAll(metroApp.transList1)
                    }
                    allRoutes.add(station1)

                    val station2 = Station(
                        path = routes[1],
                        direction = metroApp.getDirectionForSecondRoute().toString()
                    ).apply {
                        transList.addAll(metroApp.transList2)
                    }
                    allRoutes.add(station2)
                    // Convert list of stations to JSON and navigate
                    val stationsJson = Gson().toJson(allRoutes)
                    navController.navigate("station_details/$stationsJson")
                }
            }

        } else {
            Toast.makeText(context, "Please enter valid data", Toast.LENGTH_SHORT).show()
        }
    }

     fun findNearestStation(
        latitude: Double,
        longitude: Double,
        context: Context
    ): NearstStation? {

        val allStationsCoordinates = CairoLinesCoordinates(context).allMetroCoordinates();
        var shortestDistance = Double.MAX_VALUE
        var nearestStation: String? = null
        var nearestStationLocation: Location? = null

        // Iterate through each station
        allStationsCoordinates.forEach { (stationName, coordinatesString) ->
            val coordinates = coordinatesString.split(',')
            val stationLatitude = coordinates[0].toDouble()
            val stationLongitude = coordinates[1].toDouble()

            // Calculate distance
            val location1 = Location("")
            location1.latitude = latitude
            location1.longitude = longitude
            val location2 = Location("")
            location2.latitude = stationLatitude
            location2.longitude = stationLongitude
            val distance = location1.distanceTo(location2)


            // Update if this is the closest station
            if (distance < shortestDistance) {
                shortestDistance = distance.toDouble()
                nearestStation = stationName
                nearestStationLocation = location2 // Update with nearest station location
            }
        }
         Log.d("RouteHelper", "shortestDistance: $shortestDistance.")
        return nearestStation?.let {
            NearstStation(
                stationName = it,
                distance = shortestDistance,
                location = nearestStationLocation
            )
        }
    }
}
