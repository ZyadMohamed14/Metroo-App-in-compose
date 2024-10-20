package com.example.nativemetrooapp.presentation.metroo.compnents

import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.presentation.metroo.LocationViewModel


@Composable
fun AddressConfirmationDialog(
    isDialogOpen:MutableState<Boolean>,
    locationViewModel: LocationViewModel
) {
    val context = LocalContext.current
    val address = remember { mutableStateOf(("")) }
    if(isDialogOpen.value){
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null, tint = Color.Green.copy(alpha = 0.7f), modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = context.getString(R.string.confirm_your_destination_address), style = MaterialTheme.typography.titleMedium, fontSize = 20.sp, color = Color.Black.copy(alpha = 0.7f))
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = { Text(text = context.getString(R.string.confirm_address)) },
                        placeholder = { Text(text = context.getString(R.string.enter_your_address)) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = null, tint = Color.Gray)
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = MaterialTheme.shapes.medium
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (address.value.isNotEmpty()) {
                            locationViewModel.getLatLngFromAddress(address = address.value)
                            isDialogOpen.value=false
                        } else {
                            Toast.makeText(context, context.getString(R.string.please_provide_an_address), Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green.copy(alpha = 0.7f)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null, tint = Color.White)
                    Text(text = context.getString(R.string.confirm), color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { isDialogOpen.value=false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null, tint = Color.White)
                    Text(text = context.getString(R.string.cancel), color = Color.White)
                }
            }
        )
    }

}