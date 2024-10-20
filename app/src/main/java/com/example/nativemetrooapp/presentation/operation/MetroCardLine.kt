package com.example.nativemetrooapp.presentation.operation



import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.domain.MetroOperation





@Composable
fun MetroLinesScreen(context: Context) {
    val metroOperations = remember { MetroOperation.getMetroOperations(context) }

    LazyColumn {
        items(metroOperations) { operation ->
            MetroLineCard(metroOperation = operation)
        }
    }
}

@Composable
fun MetroLineCard(metroOperation: MetroOperation) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TitleText(metroOperation.name)
            DetailSection(title = stringResource(id = R.string.operatingHours), content = metroOperation.operatingHours)
            DetailSection(title = stringResource(id = R.string.lastTrainDepartures), content = metroOperation.lastTrainDepartures.joinToString("\n"))
            DetailSection(title = stringResource(id = R.string.intersections), content = metroOperation.intersections.joinToString("\n"))
            DetailSection(title = stringResource(id = R.string.tripTime), content = metroOperation.tripTime)
            DetailSection(title = stringResource(id = R.string.headway), content = metroOperation.headway)
            DetailSection(
                title = stringResource(id = R.string.trainFleetSize),
                content = "${metroOperation.trainFleetSize} ${stringResource(id = R.string.trains)} (${metroOperation.trainUnits} ${stringResource(id = R.string.unitsEach)})"
            )
            DetailSection(
                title = stringResource(id = R.string.maximumSpeed),
                content = "${metroOperation.maximumSpeed} ${stringResource(id = R.string.kmPerHour)}"
            )
            DetailSection(
                title = stringResource(id = R.string.designCapacity),
                content = "${metroOperation.designCapacity} ${stringResource(id = R.string.passengersPerTrain)}"
            )
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DetailSection(title: String, content: String) {
    SectionHeader(title)
    Text(content, fontSize = 16.sp)
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewMetroLineCard() {
//    val sampleMetroOperation = MetroOperation(
//        name = "Line 1",
//        operatingHours = "06:00 AM - 11:00 PM",
//        lastTrainDepartures = listOf("10:30 PM", "11:00 PM"),
//        intersections = listOf("Station A", "Station B"),
//        tripTime = "30 minutes",
//        headway = "5 minutes",
//        trainFleetSize = 50,
//        trainUnits = 8,
//        maximumSpeed = 80,
//        designCapacity = 200
//    )
//    MetroLineCard(metroOperation = sampleMetroOperation)
//}