package com.example.nativemetrooapp.presentation.stationsdetails

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Subway
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.data.local.StationDao
import com.example.nativemetrooapp.data.local.StationDatabase
import com.example.nativemetrooapp.domain.Station
import com.example.nativemetrooapp.presentation.history.StationViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StationItem(stationDetails: Station, navController: NavController) {
    val context = LocalContext.current
    val viewModel : StationViewModel = hiltViewModel()

    // Convert the time
    val time = formatTime(timeInMinutes = stationDetails.time.toInt(), context = LocalContext.current)

    // Determine the text direction (RTL or LTR)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {

            },
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Light background
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Origin and Destination with enhanced colors
                buildRowItem(
                    icon = Icons.Default.LocationOn,
                    header = stringResource(R.string.origin),
                    data = stationDetails.start,
                    textColor = Color(0xFF1E88E5) // Deep blue for header
                )
                buildRowItem(
                    icon = Icons.Default.Flag,
                    header = stringResource(R.string.destination),
                    data = stationDetails.end,
                    textColor = Color(0xFF1E88E5) // Deep blue for consistency
                )
                Divider(color = Color(0xFFBDBDBD), thickness = 1.dp) // Light divider

                // Time and number of stations
                buildRowItem(
                    icon = Icons.Default.AccessTime,
                    header = stringResource(R.string.time),
                    data = time,
                    textColor = Color(0xFF4CAF50) // Mint green for positive info
                )
                buildRowItem(
                    icon = Icons.Default.Subway,
                    header = stringResource(R.string.number_of_stations),
                    data = stationDetails.noOfStations.toString(),
                    textColor = Color(0xFF1E88E5) // Deep blue for stations
                )
                Divider(color = Color(0xFFBDBDBD), thickness = 1.dp)

                // Ticket price and direction
                buildRowItem(
                    icon = Icons.Default.AttachMoney,
                    header = stringResource(R.string.ticket_price),
                    data = "\$${stationDetails.ticketPrice.toString()}",
                    textColor = Color(0xFFFFA726) // Light orange for price
                )
                buildRowItem(
                    icon = Icons.Default.Navigation,
                    header = stringResource(R.string.direction),
                    data = stationDetails.direction,
                    textColor = Color(0xFF1E88E5) // Deep blue for direction
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Button to view route details
                Button(
                    onClick = {
                        // Navigate to the station details screen
                        val stationDetailsjson = Gson().toJson(stationDetails)
                        navController.navigate("route_details/$stationDetailsjson")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Mint green button
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.view_route_details),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Positioned save icon with better contrast
            IconButton(
                onClick = {
                    saveStation(context,viewModel, stationDetails)
                },
                modifier = if (isRtl) {
                    Modifier.align(Alignment.TopEnd)
                } else {
                    Modifier.align(Alignment.TopEnd)
                }
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = Color(0xFFFFA726)) // Orange save icon
            }
        }
    }
}

@Composable
fun buildRowItem(icon: ImageVector, header: String, data: String, textColor: Color = Color.Black) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp), // Slightly more padding for spacing
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = textColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$header: ",
            fontWeight = FontWeight.SemiBold, // Semi-bold for emphasis
            fontSize = 15.sp, // Larger font size for headers
            color = textColor
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = data,
            fontSize = 14.sp, // Standard size for data
            color = Color(0xFF424242) // Dark gray for body text
        )
    }
}




fun saveStation(context: Context,viewModel: StationViewModel, station: Station) {


        try {
            viewModel.insertStation(station)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Station saved successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Failed to save station", Toast.LENGTH_SHORT).show()
            }
        }

}
fun formatTime(timeInMinutes: Int, context: Context): String {
    // Get the whole number of hours
    val hours = timeInMinutes / 60

    // Get the remaining minutes
    val minutes = timeInMinutes % 60

    // Handle different scenarios based on time
    return when {
        hours > 0 && minutes > 0 -> {
            // Both hours and minutes are present
            "$hours ${if (hours == 1) context.getString(R.string.hour) else context.getString(R.string.hours)} " +
                    "${context.getString(R.string.and)} $minutes ${if (minutes == 1) context.getString(R.string.minute) else context.getString(R.string.minutes)}"
        }
        hours > 0 -> {
            // Only hours are present
            "$hours ${if (hours == 1) context.getString(R.string.hour) else context.getString(R.string.hours)}"
        }
        else -> {
            // Only minutes are present
            "$minutes ${if (minutes == 1) context.getString(R.string.minute) else context.getString(R.string.minutes)}"
        }
    }
}

