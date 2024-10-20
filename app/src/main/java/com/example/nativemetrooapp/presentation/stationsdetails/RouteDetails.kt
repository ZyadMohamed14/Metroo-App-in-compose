package com.example.nativemetrooapp.presentation.stationsdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.Station
import com.example.nativemetrooapp.ui.theme.lightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(station: Station, navigateBack: () -> Unit) {

    val layoutDirection = LocalLayoutDirection.current // Get the current layout direction
    val isRtl = layoutDirection == LayoutDirection.Rtl // Check if it's RTL (Arabic)


    val noOfStations = station.path.size.toString()


    val tiketPrice = station.ticketPrice.toString()

    val context = LocalContext.current
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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailItem(stringResource(id = R.string.stations),  noOfStations, Icons.Default.Train)
                    DetailItem(stringResource(id = R.string.time), formatTime(station.time.toInt(), context = context), Icons.Default.AccessTime)
                    DetailItem(stringResource(id = R.string.price), tiketPrice, Icons.Default.AttachMoney)
                }
                Spacer(modifier = Modifier.height(10.dp))
                OriginDestinationItem(station.start, stringResource(id = R.string.from))
                Spacer(modifier = Modifier.height(10.dp))
                OriginDestinationItem(station.end, stringResource(id = R.string.to))
                Spacer(modifier = Modifier.height(10.dp))
                StationsListItems(station)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

@Composable
fun DetailItem(header: String, data: String, icon: ImageVector) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .border(2.dp, Color.Green.copy(alpha = 0.5f))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.Green.copy(alpha = 0.5f), modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(header, fontFamily = FontFamily.Serif, color = Color.Black.copy(alpha = 0.7f), fontSize = 18.sp)
        Text(data, fontFamily = FontFamily.Serif, color = Color.Black, fontSize = 14.sp)
    }
}

@Composable
fun OriginDestinationItem(stationName: String, dir: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Green.copy(alpha = 0.5f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(dir, fontSize = 18.sp, fontFamily = FontFamily.Serif)
        Spacer(modifier = Modifier.width(8.dp))
        Text(stationName, fontSize = 18.sp, fontFamily = FontFamily.Serif, color = Color.Gray)
    }
}

@Composable
fun StationsListItems(station: Station) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(2.dp, Color.Green.copy(alpha = 0.5f))
    ) {
        Text(stringResource(id = R.string.stations), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

        LazyColumn {
            items(station.path.size) { index ->
                StationListItem(station.path[index], index, station.path.size, lightGreen, station.transList)
                if (index != station.path.lastIndex) {
                    Divider(color = lightGreen, thickness = 2.dp, modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
        }
    }
}

@Composable
fun StationListItem(stationName: String, index: Int, listSize: Int, lineColor: Color, transList: List<String>) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isTransitionStation = transList.contains(stationName)
        val displayName = if (isTransitionStation) "$stationName ( ${stringResource(id = R.string.transition_station)})" else stationName
        val displayColor = if (isTransitionStation) Color.Yellow else lineColor

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(displayColor, shape = CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color.White, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        Text(displayName, fontFamily = FontFamily.Serif, fontSize = 18.sp)
    }
}



