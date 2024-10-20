package com.example.nativemetrooapp.presentation.operation

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Train
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.MetroOperation




import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.nativemetrooapp.ui.theme.lightGreen


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen() {
    // Create coroutine scope to launch sheet operations
    val coroutineScope = rememberCoroutineScope()

    // Bottom sheet state for Metro Lines
    val metroLinesSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Bottom sheet state for Metro Pricing
    val metroPricingSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight = screenHeight * 0.7f
    var showMetroLines by remember { mutableStateOf(false) }  // State to trigger showing the Metro Lines bottom sheet
    var showMetroPricing by remember { mutableStateOf(false) }  // State to trigger showing the Metro Pricing bottom sheet

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.services)) },  // Using string resource
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Blue.copy(alpha = 0.8f))
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ServiceCard(
                title = stringResource(id = R.string.metro_operation), // Using string resource
                icon = Icons.Default.Train,
                onClick = {
                    // Show Metro Lines bottom sheet
                    coroutineScope.launch {
                        showMetroLines = true
                        metroLinesSheetState.show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ServiceCard(
                title = stringResource(id = R.string.metro_pricing), // Using string resource
                icon = Icons.Default.AttachMoney,
                onClick = {
                    // Show Metro Pricing bottom sheet
                    coroutineScope.launch {
                        showMetroPricing = true
                        metroPricingSheetState.show()
                    }
                }
            )
        }

        // Metro Lines Bottom Sheet
        if (showMetroLines) {
            ModalBottomSheet(
                sheetState = metroPricingSheetState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(halfScreenHeight),
                onDismissRequest = { showMetroLines= false }
            ) {
                // Content for Metro Pricing Bottom Sheet
                MetroLinesScreen(
                    LocalContext.current
                )
            }

        }

        // Metro Pricing Bottom Sheet
        if (showMetroPricing) {

            ModalBottomSheet(
                sheetState = metroPricingSheetState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(halfScreenHeight),
                onDismissRequest = { showMetroPricing = false }
            ) {
                // Content for Metro Pricing Bottom Sheet
                MetroPricingBottomSheet({})
            }
        }
    }
}

@Composable
fun ServiceCard(
    title: String,
    icon: ImageVector,

    onClick:  () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick).background(color = Color.Blue.copy(alpha = 0.8f), shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.White)
        }
    }
}

