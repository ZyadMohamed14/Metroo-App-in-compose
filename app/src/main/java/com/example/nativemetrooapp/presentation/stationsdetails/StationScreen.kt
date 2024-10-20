package com.example.nativemetrooapp.presentation.stationsdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.Station


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailsScreen(stations: List<Station>,navController: NavController, navigateBack: () -> Unit,) {
    val layoutDirection = LocalLayoutDirection.current // Get the current layout direction
    val isRtl = layoutDirection == LayoutDirection.Rtl // Check if it's RTL (Arabic)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.route_details)) }, // Adjust title string
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = if (isRtl) Icons.Default.ArrowForward else Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(stations) { station ->
                    StationItem(stationDetails = station,navController)
                }
            }
        }
    )
}

