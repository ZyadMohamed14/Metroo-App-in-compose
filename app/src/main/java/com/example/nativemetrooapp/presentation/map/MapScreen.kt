package com.example.nativemetrooapp.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.AppResources
import com.example.nativemetrooapp.domain.MapModel
import com.example.nativemetrooapp.ui.theme.lightGreen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap


import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
   mapModel: MapModel
) {
   val mapViewModel:MapViewModel = hiltViewModel()
    val directionStates by mapViewModel.directions.collectAsState()
    LaunchedEffect(true) {
        mapViewModel.fetchDirections(
            mapModel.currentLocation,
            mapModel.nearestStationLocation
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mapModel.currentLocation, 15f)
    }
    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ){
        Marker(
            state = MarkerState(position = mapModel.currentLocation),
            title =mapModel.currentLocationName
        )
        Marker(
            state = MarkerState(position = mapModel.nearestStationLocation),
            title = mapModel.nearestStationName
        )
        when (directionStates) {
        is AppResources.Loading -> {
            // Show loading indicator (e.g., a CircularProgressIndicator)
        }
        is AppResources.Success -> {
            val route = (directionStates as AppResources.Success).data
            // Draw polyline using route points
            if (route != null) {
                Polyline(
                    points = route.routePoints.flatten(), // Flatten the list of points if necessary
                    clickable = true,
                    color = Color.Green,
                    width = 10f,
                )
            }
        }
        is AppResources.Error -> {
            val errorMessage = (directionStates as AppResources.Error).message
            // Show error message (e.g., a Toast or a Text)
        }

            is AppResources.Idle -> TODO()
        }

    }
}

