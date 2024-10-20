package com.example.nativemetrooapp.presentation.metroo.compnents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,  // Make sure text is a String
    onTextChange: (String) -> Unit,  // String lambda for text changes
    hint: String,
    isCitySelected: Boolean,
    onTextFieldTap: () -> Unit,
    opensheet:()->Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = text,  // Should be a String
        onValueChange = onTextChange,  // Should return a String
        readOnly = isCitySelected,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isCitySelected) {
                onTextFieldTap()
            },
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.6f),
                    fontFamily = FontFamily.SansSerif // You can use GoogleFonts if integrated
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.LocationCity,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier.clickable {
                    opensheet()
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.Green,
            focusedBorderColor = Color.Green,
            unfocusedBorderColor = Color.Green,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,

    )
}
