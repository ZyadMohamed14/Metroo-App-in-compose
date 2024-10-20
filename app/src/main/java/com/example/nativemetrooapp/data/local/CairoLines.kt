package com.example.nativemetrooapp.data.local

import android.content.Context
import com.example.nativemetrooapp.R


class CairoLines(private val context: Context) {
     val cairoLine1: ArrayList<String> by lazy {
        ArrayList(context.resources.getStringArray(R.array.metro_line1).toList())
    }
    val cairoLine2: ArrayList<String> by lazy {
        ArrayList(context.resources.getStringArray(R.array.metro_line2).toList())
    }
    val cairoLine3: ArrayList<String> by lazy {
        ArrayList(context.resources.getStringArray(R.array.metro_line3).toList())
    }
    val kitKatCairoUniversityLine: List<String> by lazy {
        context.resources.getStringArray(R.array.kitkat_cairo_university_line).toList()
    }
    fun getAllLines(): List<String> {
        val allLines = ArrayList<String>()
        allLines.addAll(cairoLine1)
        allLines.addAll(cairoLine2)
        allLines.addAll(cairoLine3)
        allLines.addAll(kitKatCairoUniversityLine)
        return allLines
    }
}