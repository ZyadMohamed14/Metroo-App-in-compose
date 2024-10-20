package com.example.nativemetrooapp.domain
import android.content.Context
import com.example.nativemetrooapp.R

data class MetroOperation(
    val name: String,
    val operatingHours: String,
    val lastTrainDepartures: List<String>,
    val intersections: List<String>,
    val tripTime: String,
    val headway: String,
    val trainFleetSize: Int,
    val trainUnits: Int,
    val maximumSpeed: Int,
    val designCapacity: Int
) {
    companion object {
        fun getMetroOperations(context: Context): List<MetroOperation> {
            return listOf(
                MetroOperation(
                    name = context.getString(R.string.line1),
                    operatingHours = context.getString(R.string.operatingHoursLine1),
                    lastTrainDepartures = listOf(context.getString(R.string.lastTrainDepartureLine1)),
                    intersections = listOf(context.getString(R.string.intersectionsLine1)),
                    tripTime = context.getString(R.string.tripTimeLine1),
                    headway = context.getString(R.string.headwayLine1),
                    trainFleetSize = 62,
                    trainUnits = 3,
                    maximumSpeed = 80,
                    designCapacity = 2583
                ),
                MetroOperation(
                    name = context.getString(R.string.line2),
                    operatingHours = context.getString(R.string.operatingHoursLine2),
                    lastTrainDepartures = listOf(context.getString(R.string.lastTrainDepartureLine2)),
                    intersections = listOf(context.getString(R.string.intersectionsLine2)),
                    tripTime = context.getString(R.string.tripTimeLine2),
                    headway = context.getString(R.string.headwayLine2),
                    trainFleetSize = 45,
                    trainUnits = 2,
                    maximumSpeed = 80,
                    designCapacity = 2583
                ),
                MetroOperation(
                    name = context.getString(R.string.line3),
                    operatingHours = context.getString(R.string.operatingHoursLine3),
                    lastTrainDepartures = listOf(context.getString(R.string.lastTrainDepartureLine3)),
                    intersections = emptyList(),
                    tripTime = context.getString(R.string.tripTimeLine3),
                    headway = context.getString(R.string.headwayLine3),
                    trainFleetSize = 45,
                    trainUnits = 20,
                    maximumSpeed = 80,
                    designCapacity = 2583
                )
            )
        }
    }
}
