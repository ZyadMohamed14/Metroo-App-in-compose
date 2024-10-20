package com.example.nativemetrooapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
class Station(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val path: List<String>,
    val direction: String
) {
    lateinit var start: String
    lateinit var end: String
    var transList: MutableList<String> = mutableListOf()
    var time: Double = 0.0
    var noOfStations: Int = 0
    var ticketPrice: Double = 0.0

    init {
        calcData()
    }



    // Calculate data for the station
    private fun calcData() {
        start = path.first()
        end = path.last()
        val startIndex = path.indexOf(start)
        val endIndex = path.indexOf(end)
        noOfStations = Math.abs(endIndex - startIndex)

        ticketPrice = when {
            noOfStations <= 9 -> 8.0 // Base fare for up to 9 stations
            noOfStations in 10..16 -> 10.0 // Fare for 10 to 16 stations
            noOfStations in 17..23 -> 15.0 // Fare for 17 to 23 stations
            else -> 20.0 // Fare for more than 23 stations
        }

        val timePerStation = 2
        time = timePerStation * noOfStations.toDouble()
    }


}
