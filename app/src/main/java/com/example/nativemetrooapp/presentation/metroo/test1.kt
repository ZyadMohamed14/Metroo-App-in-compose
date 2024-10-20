package com.example.nativemetrooapp.presentation.metroo

/*
@Composable
fun MetroLineCard2(metroOperation: MetroOperation) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = metroOperation.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.operatingHours))
            Text(text = metroOperation.operatingHours, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.lastTrainDepartures))
            metroOperation.lastTrainDepartures.forEach { departure ->
                Text(text = departure, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.intersections))
            metroOperation.intersections.forEach { intersection ->
                Text(text = intersection, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.tripTime))
            Text(text = metroOperation.tripTime, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.headway))
            Text(text = metroOperation.headway, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.trainFleetSize))
            Text(
                text = "${metroOperation.trainFleetSize} ${stringResource(id = R.string.trains)} (${metroOperation.trainUnits} ${stringResource(id = R.string.unitsEach)})",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.maximumSpeed))
            Text(
                text = "${metroOperation.maximumSpeed} ${stringResource(id = R.string.kmPerHour)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            buildSectionHeader(stringResource(id = R.string.designCapacity))
            Text(
                text = "${metroOperation.designCapacity} ${stringResource(id = R.string.passengersPerTrain)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun buildSectionHeader2(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )
}
 */