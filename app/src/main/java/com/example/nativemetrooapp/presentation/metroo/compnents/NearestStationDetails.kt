package com.example.nativemetrooapp.presentation.metroo.compnents

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.data.local.CairoLines

@Composable
fun NearestStationDetails(
    nearestStation: String,
    shortestDistance: Double,
    currentLocation: Location?,
    nearestStationLocation: Location?,
    onShowMapClicked:  () -> Unit
) {
    val context = LocalContext.current
    val cairoLines = CairoLines(context)
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color.Green, RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            Text(
                text = stringResource(id = R.string.nearest_station), // Assuming localization
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = nearestStation,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DirectionsRun,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${stringResource(id = R.string.km_away_with_value)} ${
                        getDistanceString(
                            context,
                            shortestDistance
                        )
                    }",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Train,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = searchStation(
                        nearestStation,
                        cairoLines = cairoLines,
                        context = context
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            MetrooAppButton(
                icon = Icons.Default.Map,
                label = stringResource(id = R.string.show_on_map),
                isLarge = true,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                onPressed = {
                    onShowMapClicked()
                })

        }
    }

}

fun searchStation(station: String, cairoLines: CairoLines, context: Context): String {
    if (cairoLines.cairoLine1.contains(station)) {
        return context.resources.getString(R.string.metro_line_1)
    } else if (cairoLines.cairoLine2.contains(station)) {
        return context.resources.getString(R.string.metro_line_2)
    } else if (cairoLines.cairoLine3.contains(station)) {
        return context.resources.getString(R.string.metro_line_3)
    } else
        return context.resources.getString(R.string.cairo_university_branch)
}


// Function to get the distance string based on the value
@SuppressLint("DefaultLocale")
fun getDistanceString(context: Context, distance: Double): String {
    return if (distance > 1000) {
        // Convert to kilometers if greater than 1000 meters
        val distanceInKm = distance / 1000 // Convert to km
        String.format("%.2f %s", distanceInKm, context.getString(R.string.km)) // Localize "km"
    } else {
        String.format("%.0f %s", distance, context.getString(R.string.meters)) // Localize "meters"
    }
}






