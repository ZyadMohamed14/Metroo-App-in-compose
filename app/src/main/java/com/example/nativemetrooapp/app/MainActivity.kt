package com.example.nativemetrooapp.app

import DashBoardScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nativemetrooapp.domain.MapModel
import com.example.nativemetrooapp.domain.Station
import com.example.nativemetrooapp.presentation.map.MapScreen
import com.example.nativemetrooapp.presentation.stationsdetails.RouteDetailsScreen
import com.example.nativemetrooapp.presentation.stationsdetails.StationDetailsScreen
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "dashboard") {
                composable("dashboard") {
                    DashBoardScreen(navController)
                }
                composable("station_details/{stationsJson}") { backStackEntry ->
                    val stationsJson = backStackEntry.arguments?.getString("stationsJson")
                    val stations: List<Station> = if (!stationsJson.isNullOrEmpty()) {
                        Gson().fromJson(stationsJson, object : TypeToken<List<Station>>() {}.type)
                    } else {
                        emptyList() // Return an empty list if the JSON string is null or empty
                    }

                    StationDetailsScreen(stations = stations, navController = navController) { navController.popBackStack() }
                }
                composable("route_details/{stationDetailsjson}") { backStackEntry ->
                    val stationsJson = backStackEntry.arguments?.getString("stationDetailsjson")
                    val station = Gson().fromJson(stationsJson, Station::class.java)


                    RouteDetailsScreen(station){
                        navController.popBackStack()
                    }
                }
                composable(
                    "map_screen/{mapModel}",
                    arguments = listOf(navArgument("mapModel") { type = NavType.StringType })
                ) { backStackEntry ->
                    val mapModelJson = backStackEntry.arguments?.getString("mapModel") ?: ""
                    val mapModel = Gson().fromJson(mapModelJson, MapModel::class.java)
                    MapScreen(mapModel = mapModel)
                }
            }
        }
    }
}




