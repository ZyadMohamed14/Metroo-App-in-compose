package com.example.nativemetrooapp.presentation.operation



import android.content.Context
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.material3.SheetValue.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.nativemetrooapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetroPricingBottomSheet(
    onDismiss: () -> Unit
) {
    var numberOfStations by remember { mutableStateOf(1) }
    var numberOfTickets by remember { mutableStateOf(1) }
    var pricePerTicket by remember { mutableStateOf(8) }
    var printedVal by remember { mutableStateOf("") }

    // Logic to update the price based on the number of stations
    fun updatePrice() {
        pricePerTicket = when {
            numberOfStations > 23 -> 20
            numberOfStations in 17..23 -> 15
            numberOfStations in 10..16 -> 10
            else -> 8
        }
    }

    // Ticket increment logic
    fun increaseTickets() {
        numberOfTickets++
    }

    // Ticket decrement logic
    fun decreaseTickets() {
        if (numberOfTickets > 1) {
            numberOfTickets--
        }
    }

    // Main content of the bottom sheet
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.metroTicketPricing),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.enterNumberOfStations),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input field for number of stations
        TextField(
            value =printedVal,
            onValueChange = { value ->
                val parsedValue = value.toIntOrNull() ?: 1
                numberOfStations = parsedValue.coerceAtLeast(1)
                printedVal = numberOfStations.toString()
                updatePrice()
            },
            label = { Text(stringResource(R.string.numberOfStationsHint)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pricing information
        _buildPricingInfo()

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.enter_number_of_tickets))
        // Row for incrementing and decrementing tickets
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = { decreaseTickets() },
                modifier = Modifier
                    .size(48.dp), // Make the button circular
                shape = CircleShape, // Circular shape
                contentPadding = PaddingValues(0.dp) // Center the icon
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription ="decrease",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "$numberOfTickets",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedButton(
                onClick = { increaseTickets() },
                modifier = Modifier
                    .size(48.dp), // Make the button circular
                shape = CircleShape, // Circular shape
                contentPadding = PaddingValues(0.dp) // Center the icon
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total price
        Text(
            text = "${stringResource(R.string.totalPrice)}: ${pricePerTicket * numberOfTickets} ${stringResource(R.string.pounds)}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dismiss button
        Button(
            onClick = { onDismiss() },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.cancel))
        }
    }
}

// Helper composable to display pricing information
@Composable
fun _buildPricingInfo() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "- ${stringResource(R.string.pricingInfoMoreThan23)}")
        Text(text = "- ${stringResource(R.string.pricingInfo17To23)}")
        Text(text = "- ${stringResource(R.string.pricingInfo10To16)}")
        Text(text = "- ${stringResource(R.string.pricingInfo1To9)}")
    }
}

