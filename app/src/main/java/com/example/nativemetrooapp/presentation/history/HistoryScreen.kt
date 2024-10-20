package com.example.nativemetrooapp.presentation.history

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.Station
import com.example.nativemetrooapp.presentation.stationsdetails.StationItem
import com.example.nativemetrooapp.presentation.stationsdetails.formatTime
import com.example.nativemetrooapp.ui.theme.lightGreen
import com.google.gson.Gson


@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: StationViewModel = hiltViewModel()
    LaunchedEffect(key1 = true, block = {
        // we will get the student details when ever the screen is created
// Launched effect is a side effect
        viewModel.getAllStations()
    })
    val stations = mutableListOf<Station>()

    val savedStations by viewModel.stations.collectAsState()
    Log.d("rfidosiodsifodsfds",savedStations.toString())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.view_saved_trips),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            if (savedStations.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_trips_saved),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(savedStations) { station ->
                        StationCard(station) {
                            stations.add(station)
                            val stationsJson = Gson().toJson(stations)
                           // val stationsJson = Uri.encode(Gson().toJson(station))
                            navController.navigate("station_details/$stationsJson")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun StationCard(station: Station, onViewDetails: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFFF8F9FA)), // Light background for the card
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${station.start} ${stringResource(R.string.to)} ${station.end}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                ElevatedButton(
                    onClick = onViewDetails,
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = Color.White)
                    Text(
                        text = stringResource(R.string.view_route_details),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Build row items
            RowItem(icon = Icons.Default.AccessTime, header = stringResource(R.string.time), data = formatTime(station.time.toInt(), context = context), textColor = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))

            RowItem(icon = Icons.Default.Train, header = stringResource(R.string.number_of_stations), data = "${station.noOfStations}", textColor = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))

            RowItem(icon = Icons.Default.AttachMoney, header = stringResource(R.string.price), data = "\$${station.ticketPrice}", textColor = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))

            RowItem(icon = Icons.Default.Navigation, header = stringResource(R.string.direction), data = station.direction, textColor = Color.Black)
        }
    }
}

@Composable
fun RowItem(icon: ImageVector, header: String, data: String, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(8.dp)), // Add background to row items
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(24.dp).padding(end = 8.dp)) // Adjust icon size
        Text(text = "$header: $data", color = textColor, style = MaterialTheme.typography.bodyMedium)
    }
}