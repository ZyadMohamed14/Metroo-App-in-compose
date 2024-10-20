package com.example.nativemetrooapp.presentation.metroo.compnents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nativemetrooapp.ui.theme.lightGreen


@Composable
fun MetrooAppButton(
    icon: ImageVector,
    label: String,
    onPressed:   () -> Unit,
    isLarge: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onPressed,
        modifier = modifier
            .padding(8.dp)
            .size(
                width = if (isLarge) 200.dp else 100.dp,
                height = if (isLarge) 64.dp else 48.dp
            ),
        colors = ButtonDefaults.buttonColors(containerColor = lightGreen ), // Button color
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp) ,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)// Rounded corners
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
        Text(text = label, color = Color.White)
    }
}