package com.example.nativemetrooapp.presentation.metroo

import DynamicSelectTextField
import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.data.local.CairoLines
import com.example.nativemetrooapp.domain.MapModel
import com.example.nativemetrooapp.domain.NearstStation
import com.example.nativemetrooapp.helper.RouteHelper
import com.example.nativemetrooapp.presentation.metroo.compnents.MetrooAppButton
import com.example.nativemetrooapp.presentation.metroo.compnents.NearestStationDetails
import com.example.nativemetrooapp.presentation.metroo.compnents.SearchNearestStation
import com.example.nativemetrooapp.ui.theme.lightGreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun MetrooScreen(
navController: NavController

) {

    val locationViewModel: LocationViewModel = hiltViewModel() // Hilt injection

    val context = LocalContext.current
    val cairoLines = CairoLines(context)
    val stations = cairoLines.getAllLines()

    val originStation by locationViewModel.originStation // Use the ViewModel state
    val destinationStation by locationViewModel.destinationStation // Use the ViewModel state
    val showDialog = remember { mutableStateOf(false) }
    val currentLocation by locationViewModel.currentLocation.collectAsState()
    val nearestStation = remember { mutableStateOf<NearstStation?>(null) }


    val routeHelper = RouteHelper(locationViewModel,navController)
    RequestLocationPermissions(locationViewModel)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Add verticalScroll modifier
        ) {
            Text(
                text = "${stringResource(R.string.welcome)}\n${stringResource(R.string.help)}",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            DynamicSelectTextField(
                selectedValue = originStation,
                stations = stations,
                label = context.getString(R.string.origin),
                onValueChangedEvent = { newCity ->
                    locationViewModel.setOriginStation(newCity) // Update ViewModel state
                },
                modifier = Modifier.fillMaxWidth(),
                searchPlaceholder = context.getString(R.string.search)
            )
            IconButton(
                onClick = {
                    var temp = originStation
                    locationViewModel.setOriginStation(destinationStation)
                    locationViewModel.setDestinationStation(temp)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = "swap",
                    tint = lightGreen,
                    modifier = Modifier.size(30.dp)
                )
            }
            val selectedDestination = nearestStation.value?.stationName?.takeIf { it.isNotEmpty() } ?: destinationStation
            DynamicSelectTextField(
                selectedValue = selectedDestination.toString(), // Use ViewModel state or nearest station
                stations = stations,
                label = context.getString(R.string.destination),
                onValueChangedEvent = { newCity ->
                    nearestStation.value?.stationName = ""
                    locationViewModel.setDestinationStation(newCity) // Update ViewModel state
                },
                modifier = Modifier.fillMaxWidth(),
                searchPlaceholder = context.getString(R.string.search)
            )

            Spacer(modifier = Modifier.height(32.dp))
            MetrooAppButton(
                icon = Icons.Default.Route,
                label = stringResource(R.string.show_my_route),
                onPressed = {
                    routeHelper.findMyRoute(
                        origin = originStation,
                        destination = destinationStation,
                        context = context,
                        cairoLines = cairoLines
                    )
                },
                isLarge = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))
            SearchNearestStation(
                locationViewModel = locationViewModel,
                context = context,
                isDialogOpen = showDialog,
                routeHelper = routeHelper,
                nearestStation = nearestStation,
            )
            Spacer(modifier = Modifier.height(32.dp))
            currentLocation?.let {
                val nStation = routeHelper.findNearestStation(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    context = context
                )
                if (nStation != null) {
                    NearestStationDetails(
                        shortestDistance = nStation.distance,
                        nearestStation = nStation.stationName,
                        currentLocation = currentLocation,
                        nearestStationLocation = nStation.location,
                        onShowMapClicked = {
                            val mapModel = MapModel(
                                currentLocationName = context.getString(R.string.currentLocation),
                                nearestStationName = nStation.stationName,
                                currentLocation = LatLng(currentLocation!!.latitude, currentLocation!!.longitude),
                                nearestStationLocation = LatLng(nStation.location?.latitude ?: 0.0, nStation.location?.longitude ?: 0.0),
                                )
                            val mapJson = Gson().toJson(mapModel)
                            navController.navigate("map_screen/$mapJson")

                        }
                    )
                }
            }
        }
    }


}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermissions(
    locationViewModel: LocationViewModel
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Request permissions if not granted
    LaunchedEffect(key1 = permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            locationViewModel.fetchCurrentLocation()  // Fetch location when permissions are granted
        } else {
            permissionState.launchMultiplePermissionRequest()  // Request permissions
        }
    }

}




