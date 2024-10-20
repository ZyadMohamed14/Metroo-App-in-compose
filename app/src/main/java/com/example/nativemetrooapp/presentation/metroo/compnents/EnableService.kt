@file:Suppress("DEPRECATION")

package com.example.nativemetrooapp.presentation.metroo.compnents

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.ui.theme.babyBlue

@Composable
fun EnableServiceText(getCurrentLocation:  () -> Unit) {

    val betterExperienceText = stringResource(id = R.string.better_experience)
    val enableLocationText = stringResource(id = R.string.enable_location_services)
    val annotatedString = buildAnnotatedString {
        withStyle(style = androidx.compose.ui.text.SpanStyle(color = Color.Black)) {
            append(betterExperienceText)
        }
        withStyle(
            style = androidx.compose.ui.text.SpanStyle(
                color = babyBlue,

            )
        ) {
            append(enableLocationText)
        }
    }

    // ClickableText to handle clicks
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let {
                getCurrentLocation()
            }
        },
        style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
    )
}
