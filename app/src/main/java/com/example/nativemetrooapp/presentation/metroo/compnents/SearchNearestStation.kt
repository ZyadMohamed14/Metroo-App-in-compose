package com.example.nativemetrooapp.presentation.metroo.compnents

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.NearstStation
import com.example.nativemetrooapp.domain.AppResources
import com.example.nativemetrooapp.helper.RouteHelper
import com.example.nativemetrooapp.presentation.metroo.LocationViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchNearestStation(
    context: Context,
    locationViewModel: LocationViewModel,
    routeHelper: RouteHelper,
    nearestStation: MutableState<NearstStation?>,
    isDialogOpen: MutableState<Boolean>
) {

    val locationState = locationViewModel.location.collectAsState()
    var isLoading = remember { mutableStateOf(false) }

    val isnearstLocationrequested = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Fill the maximum width and add padding
        verticalAlignment = Alignment.CenterVertically, // Align items vertically centered
        horizontalArrangement = Arrangement.SpaceBetween // Space between items
    ) {
        Text(
            text = stringResource(id = R.string.search_nearest_station), // Replace with your string translation function
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.W700,
                fontSize = 15.sp // Adjust to your needs
            ),
            textAlign = TextAlign.Start // Align text to the start
        )
        Spacer(modifier = Modifier.width(8.dp)) // Spacer for width
        MetrooAppButton(
            icon = Icons.Filled.Search,
            label = context.getString(R.string.search), // Replace with your string translation function
            onPressed = {
                isDialogOpen.value = true

            } // Function call for button action
        )
        if (isnearstLocationrequested.value) {
            when (val state = locationState.value) {
                is AppResources.Error -> {
                    isLoading.value = false

                    Toast.makeText(
                        context,
                        "Error Fetching Location${state.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AppResources.Idle -> {}
                is AppResources.Loading -> {
                    isLoading.value = true
                }

                is AppResources.Success -> {
                    nearestStation.value = routeHelper.findNearestStation(
                        latitude = state.data?.first ?: 0.0,
                        longitude = state.data?.second ?: 0.0,
                        context = context
                    )
                    Log.d("nearestStation", "${nearestStation.value?.stationName}")
                    isLoading.value = false
                    Toast.makeText(context, "${state.data?.first}", Toast.LENGTH_SHORT).show()
                }
            }


        }
        isnearstLocationrequested.value = isDialogOpen.value
        if (isLoading.value) {
            CircularProgressIndicator()
        }
        if (isDialogOpen.value) {

            AddressConfirmationDialog(
                locationViewModel = locationViewModel,
                isDialogOpen = isDialogOpen
            )
        }


    }
}
