package com.example.nativemetrooapp.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val babyBlue = Color(0xFF028AF8)
// Define your static custom colors
val GreenAccentPrimaryValue = Color(0xFF69F0AE)

// Define your color palette similar to MaterialAccentColor in Flutter
val GreenAccent = mapOf(
    100 to Color(0xFFB9F6CA),
    200 to GreenAccentPrimaryValue, // Primary value
    400 to Color(0xFF00E676),
    700 to Color(0xFF00C853)
)
val lightGreen = GreenAccent[100] ?: Color.Unspecified
val primaryGreen = GreenAccent[200] ?: Color.Unspecified
