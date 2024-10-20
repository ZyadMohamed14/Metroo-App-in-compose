package com.example.nativemetrooapp.data.local

import android.content.Context
import android.util.Log
import com.example.nativemetrooapp.R

class MetroApp(
    val start: String,
    val end: String,
    cairoLines: CairoLines,
    private val context: Context
) {
    // Define your metro lines here
    private val metroLine1: List<String>
    private val metroLine2: List<String>
    private val metroLine3: List<String>
    private val cairoKitKateBranch: List<String>

    private val routes: MutableList<List<String>> = mutableListOf()
    private val directionForFirstRoute = StringBuilder()
    private val directionForSecondRoute = StringBuilder()
    private var direction: String = "" // For single route
    private var transitionStation1: String = ""

    val transList1: MutableList<String> = mutableListOf()
    val transList2: MutableList<String> = mutableListOf()


    // Variables for translation (assuming you have some translation mechanism in place)
    private val andChangeDirectionAt = context.getString(R.string.and_change_direction_at)
    private val changeDirectionTo = context.getString(R.string.change_direction_to)
    private val takeDirectionTo = context.getString(R.string.take_direction_to)
    private val thenTakeDirectionTo = context.getString(R.string.then_take_direction_to)
    private val youAreAt = context.getString(R.string.you_are_at)
    private val sadat = context.getString(R.string.sadat)
    private val nasser = context.getString(R.string.nasser)
    private val cairoUniversity = context.getString(R.string.cairo_university)
    private val elmounib = context.getString(R.string.el_mounib)
    private val attaba = context.getString(R.string.attaba)
    private val shobraElKheima = context.getString(R.string.shobra_el_kheima)
    private val shohada = context.getString(R.string.al_shohadaa)
    private val elMarg = context.getString(R.string.el_marg)
    private val helwan = context.getString(R.string.helwan)
    private val kitkat = context.getString(R.string.kitkat)
    private val adlyMansour = context.getString(R.string.adly_mansour)
    private val roadElFargCorr = context.getString(R.string.road_el_farg_corr)

    init {
        // Initialize metro lines based on the current language
        metroLine1 = cairoLines.cairoLine1
        metroLine2 = cairoLines.cairoLine2
        metroLine3 = cairoLines.cairoLine3
        cairoKitKateBranch = cairoLines.kitKatCairoUniversityLine

        Log.d("MetroApp", "metroLine1: ${metroLine1.toString()}")
        Log.d("MetroApp", "metroLine2: ${metroLine2.toString()}")
        Log.d("MetroApp", "metroLine3: ${metroLine3.toString()}")
        Log.d("MetroApp", "cairoKitKateBranch: ${cairoKitKateBranch.toString()}")

    }

    fun getDirection(): String {
        return direction // Accessing the property directly assuming it's changed from private to public or appropriate access modifier
    }

    fun getRoutes(): List<List<String>> {
        return routes // Accessing the property directly
    }

    fun getDirectionForFirstRoute(): StringBuilder {
        return directionForFirstRoute // Kotlin uses StringBuilder instead of StringBuffer
    }

    fun getDirectionForSecondRoute(): StringBuilder {
        return directionForSecondRoute
    }

    fun searchPath() {
        if (isSameLine(start, end)) {
            getPathIfSameLine()
        } else {
            searchInManyLines()
        }
    }

    fun isValidData(): Boolean {
        if (start.isEmpty() || end.isEmpty()) return false
        if (start == end) return false
        return isValidStation(start) && isValidStation(end)
    }

    private fun isValidStation(station: String): Boolean {
        return metroLine1.contains(station) ||
                metroLine2.contains(station) ||
                metroLine3.contains(station) ||
                cairoKitKateBranch.contains(station)
    }

    fun isSameLine(start: String, end: String): Boolean {
        return isOnSameLine(metroLine1, start, end) ||
                isOnSameLine(metroLine2, start, end) ||
                isOnSameLine(metroLine3, start, end) ||
                isOnSameLine(cairoKitKateBranch, start, end)
    }

    private fun isOnSameLine(line: List<String>, start: String, end: String): Boolean {
        return line.contains(start) && line.contains(end)
    }

    fun getPathIfSameLine() {
        val path: MutableList<String> = mutableListOf()

        when {
            metroLine1.contains(start) && metroLine1.contains(end) -> {
                path.addAll(getSingleRoute(metroLine1, start, end))
            }

            metroLine2.contains(start) && metroLine2.contains(end) -> {
                path.addAll(getSingleRoute(metroLine2, start, end))
            }

            metroLine3.contains(start) && metroLine3.contains(end) -> {
                path.addAll(getSingleRoute(metroLine3, start, end))
            }

            cairoKitKateBranch.contains(start) && cairoKitKateBranch.contains(end) -> {
                path.addAll(getSingleRoute(cairoKitKateBranch, start, end))
            }
        }

        routes.add(path)
    }

    private fun getSingleRoute(line: List<String>, start: String, end: String): List<String> {
        println(start)
        println(end)
        println(line.size)

        val startIndex = line.indexOf(start)
        val endIndex = line.indexOf(end)

        val subListPath: List<String> = if (startIndex < endIndex) {
            // Going from start to end
            direction = "$takeDirectionTo ${line.last()}"
            line.subList(startIndex, endIndex + 1)
        } else {
            // Going from end to start (reversed)
            direction = "$takeDirectionTo ${line.first()}"
            line.subList(endIndex, startIndex + 1).reversed()
        }

        println(subListPath.size)
        return subListPath
    }

    private fun searchInManyLines() {
        when {
            metroLine1.contains(start) && metroLine2.contains(end) -> {
                searchRoutesFromLine1ToLine2()
            }

            metroLine2.contains(start) && metroLine1.contains(end) -> {
                 searchRoutesFromLine2ToLine1()
            }

            metroLine2.contains(start) && metroLine3.contains(end) -> {
                 searchRoutesFromLine2ToLine3()
            }

            metroLine3.contains(start) && metroLine2.contains(end) -> {
                 searchRoutesFromLine3ToLine2()
            }

            metroLine1.contains(start) && metroLine3.contains(end) -> {
                 searchRoutesFromLine1ToLine3()
            }

            metroLine3.contains(start) && metroLine1.contains(end) -> {
                searchRoutesFromLine3ToLine1()
            }

            metroLine1.contains(start) && cairoKitKateBranch.contains(end) -> {
                searchRoutesFromLine1ToCairoKitKateBranch()
            }

            cairoKitKateBranch.contains(start) && metroLine1.contains(end) -> {
                searchRoutesFromCairoKitKateBranchToLine1()
            }

            metroLine2.contains(start) && cairoKitKateBranch.contains(end) -> {
                searchRoutesFromLine2ToCairoKitKateBranch()
            }

            cairoKitKateBranch.contains(start) && metroLine2.contains(end) -> {
                searchRoutesFromCairoKitKateBranchToLine2()
            }

            (metroLine3.contains(start) && cairoKitKateBranch.contains(end)) ||
                    (cairoKitKateBranch.contains(start) && metroLine3.contains(end)) -> {
                  searchRoutesFromLine3ToCairoKitKateBranch()
            }
        }
    }

    private fun searchRoutesFromLine1ToLine2() {
        println("_searchRoutesFromLine1ToLine2")
        val sadatLine = mutableListOf<String>()
        val shohadaaLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        val startIndex = metroLine1.indexOf(start)
        val sadatIndexAtLine1 = metroLine1.indexOf(sadat)
        val shohadaaIndexAtLine1 =
            metroLine1.indexOf(shohada) // Assuming this was meant to relate to Shohadaa
        val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
        val shohadaaIndexAtLine2 =
            metroLine2.indexOf(shohada) // Assuming this was meant to relate to Shohadaa
        val endIndex = metroLine2.indexOf(end)
        transList1.add(sadat)
        transList2.add(shohada)
        // Operations at line 1
        when {
            startIndex < sadatIndexAtLine1 && startIndex < shohadaaIndexAtLine1 -> {
                sadatLine.addAll(metroLine1.subList(startIndex, sadatIndexAtLine1))
                shohadaaLine.addAll(metroLine1.subList(startIndex, shohadaaIndexAtLine1))
                directionForFirstRoute.append("$takeDirectionTo $elMarg $andChangeDirectionAt $sadat")
                directionForSecondRoute.append("$takeDirectionTo $elMarg $andChangeDirectionAt $shohada")
            }

            startIndex > shohadaaIndexAtLine1 && startIndex > sadatIndexAtLine1 -> {
                sadatLine.addAll(metroLine1.subList(sadatIndexAtLine1 + 1, startIndex))
                shohadaaLine.addAll(metroLine1.subList(shohadaaIndexAtLine1 + 1, startIndex))
                sadatLine.reverse()
                shohadaaLine.reverse()
                directionForFirstRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $sadat")
                directionForSecondRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $shohada")
            }

            startIndex in (sadatIndexAtLine1 + 1)..<shohadaaIndexAtLine1 -> {
                directionForFirstRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $sadat")
                directionForSecondRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $shohada")
                shohadaaLine.addAll(metroLine1.subList(startIndex, shohadaaIndexAtLine1))
                sadatLine.addAll(metroLine1.subList(sadatIndexAtLine1 + 1, startIndex + 1))
                sadatLine.reverse()
            }

            else -> {
                // You are at Sadat or Shohadaa
                directionForFirstRoute.append("$youAreAt $sadat")
                directionForSecondRoute.append("$youAreAt $shohada")
            }
        }

        // Operations at line 2
        when {
            endIndex < sadatIndexAtLine2 && endIndex < shohadaaIndexAtLine2 -> {
                subLine.addAll(metroLine2.subList(endIndex, shohadaaIndexAtLine2 + 1))
                shohadaaLine.addAll(subLine.reversed())
                subLine.clear()
                subLine.addAll(metroLine2.subList(endIndex, sadatIndexAtLine2 + 1))
                sadatLine.addAll(subLine.reversed())
                subLine.clear()
                directionForFirstRoute.append("$thenTakeDirectionTo $shobraElKheima")
                directionForSecondRoute.append("$thenTakeDirectionTo $shobraElKheima")
            }

            endIndex > sadatIndexAtLine2 && endIndex > shohadaaIndexAtLine2 -> {
                sadatLine.addAll(metroLine2.subList(sadatIndexAtLine2, endIndex + 1))
                shohadaaLine.addAll(metroLine2.subList(shohadaaIndexAtLine2, endIndex + 1))
                directionForFirstRoute.append("$thenTakeDirectionTo $elmounib")
                directionForSecondRoute.append("$thenTakeDirectionTo $elmounib")
            }

            endIndex in (shohadaaIndexAtLine2 + 1)..<sadatIndexAtLine2 -> {
                sadatLine.addAll(metroLine2.subList(endIndex, sadatIndexAtLine2 + 1))
                subLine.addAll(metroLine2.subList(shohadaaIndexAtLine2, endIndex + 1))
                shohadaaLine.addAll(subLine.reversed())
                subLine.clear()
                directionForFirstRoute.append("$thenTakeDirectionTo $elmounib")
                directionForSecondRoute.append("$thenTakeDirectionTo $shobraElKheima")
            }

            else -> {
                // You are at Sadat or Shohadaa
                directionForFirstRoute.append("$youAreAt $sadat")
                directionForSecondRoute.append("$youAreAt $shobraElKheima")
                sadatLine.add(sadat)
                shohadaaLine.add(shobraElKheima)
            }
        }

        routes.add(sadatLine)
        routes.add(shohadaaLine)
    }
    private fun searchRoutesFromLine2ToLine1() {
        val sadatLine = mutableListOf<String>()
        val shohadaaLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        transList1.add(sadat)
        transList2.add(shohada)

        if (metroLine2.contains(start) && metroLine1.contains(end)) {
            val startIndex = metroLine2.indexOf(start)
            val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
            val shohadaaIndexAtLine2 = metroLine2.indexOf(shohada) // Assuming this relates to Shohadaa
            val sadatIndexAtLine1 = metroLine1.indexOf(sadat)
            val shohadaaIndexAtLine1 = metroLine1.indexOf(shohada) // Assuming this relates to Shohadaa
            val endIndex = metroLine1.indexOf(end)

            // Operations at Line 2
            when {
                startIndex < sadatIndexAtLine2 && startIndex < shohadaaIndexAtLine2 -> {
                    sadatLine.addAll(metroLine2.subList(startIndex, sadatIndexAtLine2))
                    shohadaaLine.addAll(metroLine2.subList(startIndex, shohadaaIndexAtLine2))
                    directionForFirstRoute.append("$takeDirectionTo $elmounib $andChangeDirectionAt $sadat\n")
                    directionForSecondRoute.append("$takeDirectionTo $elmounib $andChangeDirectionAt $shohada\n")
                }
                startIndex > sadatIndexAtLine2 && startIndex > shohadaaIndexAtLine2 -> {
                    sadatLine.addAll(metroLine2.subList(sadatIndexAtLine2 + 1, startIndex + 1))
                    shohadaaLine.addAll(metroLine2.subList(shohadaaIndexAtLine2 + 1, startIndex + 1))
                    sadatLine.reverse()
                    shohadaaLine.reverse()
                    directionForFirstRoute.append("$takeDirectionTo $shobraElKheima $andChangeDirectionAt $sadat\n")
                    directionForSecondRoute.append("$takeDirectionTo $shobraElKheima $andChangeDirectionAt $shohada\n")
                }
                startIndex in (shohadaaIndexAtLine2 + 1)..<sadatIndexAtLine2 -> {
                    sadatLine.addAll(metroLine2.subList(sadatIndexAtLine2 + 1, startIndex + 1))
                    shohadaaLine.addAll(metroLine2.subList(startIndex, shohadaaIndexAtLine2))
                    sadatLine.reverse()
                }
                else -> {
                    directionForFirstRoute.append("$youAreAt $sadat")
                    directionForSecondRoute.append("$youAreAt $shohada")
                }
            }

            // Operations at Line 1
            when {
                endIndex < sadatIndexAtLine1 && endIndex < shohadaaIndexAtLine1 -> {
                    subLine.addAll(metroLine1.subList(endIndex, sadatIndexAtLine1 + 1))
                    subLine.reverse()
                    sadatLine.addAll(subLine)
                    subLine.clear()
                    subLine.addAll(metroLine1.subList(endIndex, shohadaaIndexAtLine1 + 1))
                    subLine.reverse()
                    shohadaaLine.addAll(subLine)
                    subLine.clear()
                    directionForFirstRoute.append("$thenTakeDirectionTo $elMarg ")
                    directionForSecondRoute.append("$thenTakeDirectionTo $elMarg ")
                }
                endIndex > sadatIndexAtLine1 && endIndex > shohadaaIndexAtLine1 -> {
                    sadatLine.addAll(metroLine1.subList(sadatIndexAtLine1, endIndex + 1))
                    shohadaaLine.addAll(metroLine1.subList(shohadaaIndexAtLine1, endIndex + 1))
                    directionForFirstRoute.append("$thenTakeDirectionTo $helwan ")
                    directionForSecondRoute.append("$thenTakeDirectionTo $helwan ")
                }
                endIndex in (shohadaaIndexAtLine1 + 1)..<sadatIndexAtLine1 -> {
                    sadatLine.addAll(metroLine1.subList(endIndex, sadatIndexAtLine1 + 1))
                    subLine.addAll(metroLine1.subList(shohadaaIndexAtLine1, endIndex + 1))
                    subLine.reverse()
                    shohadaaLine.addAll(subLine)
                    subLine.clear()
                    directionForFirstRoute.append("$thenTakeDirectionTo $helwan ")
                    directionForSecondRoute.append("$thenTakeDirectionTo $elMarg ")
                }
                else -> {
                    directionForFirstRoute.append("$youAreAt $sadat")
                    directionForSecondRoute.append("$youAreAt $shohada")
                    sadatLine.add(sadat)
                    shohadaaLine.add(shobraElKheima)
                }
            }

            routes.add(sadatLine)
            routes.add(shohadaaLine)
        }
    }
    private fun searchRoutesFromLine2ToLine3() {
        val attabaIndexAtLine2 = metroLine2.indexOf(attaba)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        val attabaIndexAtLine3 = metroLine3.indexOf(attaba)
        val kitKateIndex = metroLine3.indexOf(kitkat)
        val startIndex = metroLine2.indexOf(start)
        val endIndex = metroLine3.indexOf(end)

        val attabaLine = mutableListOf<String>()
        val cairoLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        transList1.add(attaba)
        transList2.addAll(listOf(cairoUniversity, kitkat))

        // Operations at line 2
        when {
            startIndex < attabaIndexAtLine2 && startIndex < cairoIndexAtLine2 -> {
                directionForFirstRoute.append("$takeDirectionTo $elmounib $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $elmounib $andChangeDirectionAt $cairoUniversity\n$andChangeDirectionAt $kitkat")
                attabaLine.addAll(metroLine2.subList(startIndex, attabaIndexAtLine2))
                cairoLine.addAll(metroLine2.subList(startIndex, cairoIndexAtLine2))
                subLine.addAll(cairoKitKateBranch)
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.clear()
            }
            startIndex > attabaIndexAtLine2 && startIndex > cairoIndexAtLine2 -> {
                directionForFirstRoute.append("$takeDirectionTo $shobraElKheima $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $shobraElKheima $andChangeDirectionAt $cairoUniversity\n$andChangeDirectionAt $kitkat")
                subLine.addAll(metroLine2.subList(attabaIndexAtLine2 + 1, startIndex + 1))
                subLine.reverse()
                attabaLine.addAll(subLine)
                subLine.clear()
                subLine.addAll(metroLine2.subList(cairoIndexAtLine2 + 1, startIndex + 1))
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.addAll(cairoKitKateBranch)
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.clear()
            }
            startIndex in (attabaIndexAtLine2 + 1)..<cairoIndexAtLine2 -> {
                directionForFirstRoute.append("$takeDirectionTo $shobraElKheima $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $elmounib $andChangeDirectionAt $cairoUniversity\n$andChangeDirectionAt $kitkat")
                subLine.addAll(metroLine2.subList(attabaIndexAtLine2 + 1, startIndex + 1))
                subLine.reverse()
                attabaLine.addAll(subLine)
                subLine.clear()
                cairoLine.addAll(metroLine2.subList(startIndex, cairoIndexAtLine2))
                subLine.addAll(cairoKitKateBranch)
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.clear()
            }
            else -> {
                directionForFirstRoute.append("$youAreAt $attaba")
                directionForSecondRoute.append("$youAreAt $cairoUniversity")
            }
        }

        // Operations at line 3
        when {
            endIndex > attabaIndexAtLine3 && endIndex > kitKateIndex -> {
                directionForFirstRoute.append("$thenTakeDirectionTo $adlyMansour")
                directionForSecondRoute.append("$thenTakeDirectionTo $adlyMansour")
                attabaLine.addAll(metroLine3.subList(attabaIndexAtLine3, endIndex + 1))
                cairoLine.addAll(metroLine3.subList(kitKateIndex + 1, endIndex + 1))
            }
            endIndex < attabaIndexAtLine3 && endIndex < kitKateIndex -> {
                directionForFirstRoute.append("$thenTakeDirectionTo $roadElFargCorr")
                directionForSecondRoute.append("$thenTakeDirectionTo $roadElFargCorr")
                subLine.addAll(metroLine3.subList(endIndex, attabaIndexAtLine3 + 1))
                subLine.reverse()
                attabaLine.addAll(subLine)
                subLine.clear()
                subLine.addAll(metroLine3.subList(endIndex, kitKateIndex))
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.clear()
            }
            endIndex in (kitKateIndex + 1)..<attabaIndexAtLine3 -> {
                directionForFirstRoute.append("$thenTakeDirectionTo $adlyMansour")
                directionForSecondRoute.append("$thenTakeDirectionTo $roadElFargCorr")
                attabaLine.addAll(metroLine3.subList(endIndex, attabaIndexAtLine3 + 1))
                subLine.addAll(metroLine3.subList(kitKateIndex + 1, endIndex + 1))
                subLine.reverse()
                cairoLine.addAll(subLine)
                subLine.clear()
            }
            else -> {
                attabaLine.add(attaba)
                cairoLine.add(kitkat)
            }
        }

        routes.add(attabaLine)
        routes.add(cairoLine)
    }
    private fun searchRoutesFromLine3ToLine2() {
        val attabaLine = mutableListOf<String>()
        val kitKateLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        val startIndex = metroLine3.indexOf(start)
        val attabaIndexAtLine3 = metroLine3.indexOf(attaba)
        val kitKateIndex = metroLine3.indexOf(kitkat)
        val kitKateIndexAtCairoBranch = cairoKitKateBranch.indexOf(kitkat)
        val attabaIndexAtLine2 = metroLine2.indexOf(attaba)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        val cairoIndexAtCairoBranch = cairoKitKateBranch.indexOf(cairoUniversity)
        val endIndex = metroLine2.indexOf(end)

        transList1.add(attaba)
        transList2.addAll(listOf(cairoUniversity, kitkat))

        // Operations at Line 3
        when {
            startIndex < attabaIndexAtLine3 && startIndex < kitKateIndex -> {
                attabaLine.addAll(metroLine3.subList(startIndex, attabaIndexAtLine3 + 1))
                kitKateLine.addAll(metroLine3.subList(startIndex, kitKateIndex + 1))

                directionForFirstRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $kitkat\n")
            }
            startIndex > attabaIndexAtLine3 && startIndex > kitKateIndex -> {
                attabaLine.addAll(metroLine3.subList(attabaIndexAtLine3 + 1, startIndex + 1).reversed())
                kitKateLine.addAll(metroLine3.subList(kitKateIndex + 1, startIndex + 1).reversed())
                kitKateLine.addAll(cairoKitKateBranch.subList(kitKateIndexAtCairoBranch, cairoIndexAtCairoBranch))

                directionForFirstRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $kitkat\n")
            }
            startIndex in (kitKateIndex + 1)..<attabaIndexAtLine3 -> {
                attabaLine.addAll(metroLine3.subList(startIndex, attabaIndexAtLine3))
                kitKateLine.addAll(metroLine3.subList(kitKateIndex + 1, startIndex + 1).reversed())
                kitKateLine.addAll(cairoKitKateBranch.subList(kitKateIndexAtCairoBranch, cairoIndexAtCairoBranch))

                directionForFirstRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $attaba\n")
                directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $kitkat\n")
            }
            else -> {
                // You are at Attaba or KitKate
                if (startIndex == attabaIndexAtLine3) {
                    directionForFirstRoute.append("$youAreAt $attaba ")
                    directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $kitkat ")
                    subLine.addAll(metroLine3.subList(kitKateIndex + 1, startIndex + 1).reversed())
                    kitKateLine.addAll(subLine)
                    subLine.clear()
                    kitKateLine.addAll(cairoKitKateBranch)
                } else {
                    directionForFirstRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $attaba ")
                    directionForSecondRoute.append("$youAreAt $kitkat, Then $andChangeDirectionAt $cairoUniversity\n")
                    attabaLine.addAll(metroLine3.subList(kitKateIndex, attabaIndexAtLine3))
                    kitKateLine.addAll(cairoKitKateBranch)
                }
            }
        }

        // Operations at Line 2
        when {
            endIndex < attabaIndexAtLine2 && endIndex < cairoIndexAtLine2 -> {
                subLine.addAll(metroLine2.subList(endIndex, attabaIndexAtLine2 + 1).reversed())
                attabaLine.addAll(subLine)
                subLine.clear()
                subLine.addAll(metroLine2.subList(endIndex, cairoIndexAtLine2 + 1).reversed())
                kitKateLine.addAll(subLine)
                subLine.clear()

                directionForFirstRoute.append("$thenTakeDirectionTo $shobraElKheima ")
                directionForSecondRoute.append("$thenTakeDirectionTo $shobraElKheima ")
            }
            endIndex > attabaIndexAtLine2 && endIndex > cairoIndexAtLine2 -> {
                attabaLine.addAll(metroLine2.subList(attabaIndexAtLine2, endIndex + 1))
                kitKateLine.addAll(metroLine2.subList(cairoIndexAtLine2 + 1, endIndex + 1))

                directionForFirstRoute.append("$thenTakeDirectionTo $elmounib ")
                directionForSecondRoute.append("$thenTakeDirectionTo $elmounib ")
            }
            endIndex in (attabaIndexAtLine2 + 1)..<cairoIndexAtLine2 -> {
                subLine.addAll(metroLine2.subList(endIndex, cairoIndexAtLine2 + 1).reversed())
                kitKateLine.addAll(subLine)
                subLine.clear()
                attabaLine.addAll(metroLine2.subList(attabaIndexAtLine2, endIndex + 1))

                directionForFirstRoute.append("$thenTakeDirectionTo $shobraElKheima ")
                directionForSecondRoute.append("$thenTakeDirectionTo $elmounib ")
            }
            else -> {
                // You are at Attaba or CairoUniversity
                attabaLine.add(attaba)
                kitKateLine.add(cairoUniversity)
            }
        }

        routes.add(attabaLine)
        routes.add(kitKateLine)
    }
    private fun searchRoutesFromLine1ToLine3() {
        val startIndex = metroLine1.indexOf(start)
        val nasserIndexAtLine1 = metroLine1.indexOf(nasser)
        val sadatIndexAtLine1 = metroLine1.indexOf(sadat)
        val nasserLine = mutableListOf<String>()
        val sadatLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        transList1.add(nasser)
        transList2.addAll(listOf(sadat, cairoUniversity))

        // Operations in Line 1
        when {
            startIndex < nasserIndexAtLine1 && startIndex < sadatIndexAtLine1 -> {
                directionForFirstRoute.append("${takeDirectionTo} $elMarg ${andChangeDirectionAt} $nasser ")
                directionForSecondRoute.append("${takeDirectionTo} $elMarg ${andChangeDirectionAt} $sadat ${thenTakeDirectionTo} ${elmounib} ${andChangeDirectionAt} ${cairoUniversity}")
                nasserLine.addAll(metroLine1.subList(startIndex, nasserIndexAtLine1))
                sadatLine.addAll(metroLine1.subList(startIndex, sadatIndexAtLine1))
            }
            startIndex > nasserIndexAtLine1 && startIndex > sadatIndexAtLine1 -> {
                directionForFirstRoute.append("${takeDirectionTo} $helwan ${andChangeDirectionAt} $nasser ")
                directionForSecondRoute.append("${takeDirectionTo} $helwan ${andChangeDirectionAt} $sadat ${thenTakeDirectionTo} ${elmounib} ${andChangeDirectionAt} ${cairoUniversity}")
                subLine.addAll(metroLine1.subList(sadatIndexAtLine1 + 1, startIndex).reversed())
                sadatLine.addAll(subLine)
                subLine.clear()
                subLine.addAll(metroLine1.subList(nasserIndexAtLine1 + 1, startIndex).reversed())
                nasserLine.addAll(subLine)
            }
            else -> {
                // You are at Sadat or Shohadaa
                directionForFirstRoute.append("$youAreAt $nasser")
                directionForSecondRoute.append("$youAreAt $sadat")
            }
        }

        // Operations in Line 3
        val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        Log.d("MetroApp", "sadatIndexAtLine2: $sadatIndexAtLine2, cairoIndexAtLine2: $cairoIndexAtLine2")
        sadatLine.addAll(metroLine2.subList(sadatIndexAtLine2, cairoIndexAtLine2))
        subLine.addAll(cairoKitKateBranch)
        subLine.reversed()
        sadatLine.addAll(subLine)
        subLine.clear()

        val endIndex = metroLine3.indexOf(end)
        val nasserIndexAtLine3 = metroLine3.indexOf(nasser)
        val kitKateIndex = metroLine3.indexOf(kitkat)
        Log.d("MetroApp", "endIndex: $endIndex, nasserIndexAtLine3: $nasserIndexAtLine3, kitKateIndex: $kitKateIndex")
        when {
            endIndex > nasserIndexAtLine3 && endIndex > kitKateIndex -> {
                directionForFirstRoute.append(" $takeDirectionTo $adlyMansour")
                directionForSecondRoute.append(" $takeDirectionTo $adlyMansour")
                nasserLine.addAll(metroLine3.subList(nasserIndexAtLine3, endIndex + 1))
                sadatLine.addAll(metroLine3.subList(kitKateIndex + 1, endIndex + 1))
            }
            endIndex < nasserIndexAtLine3 && endIndex < kitKateIndex -> {
                directionForFirstRoute.append("$takeDirectionTo $roadElFargCorr")
                directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr")
                subLine.addAll(metroLine3.subList(endIndex, nasserIndexAtLine3 + 1).reversed())
                nasserLine.addAll(subLine)
                subLine.clear()
                subLine.addAll(metroLine3.subList(endIndex, kitKateIndex).reversed())
                sadatLine.addAll(subLine)
                subLine.clear()
            }
            endIndex > kitKateIndex && endIndex < nasserIndexAtLine3 -> {
                directionForFirstRoute.append("$takeDirectionTo $adlyMansour")
                directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr")
                nasserLine.addAll(metroLine3.subList(endIndex, nasserIndexAtLine3 + 1))
                subLine.addAll(metroLine3.subList(kitKateIndex + 1, endIndex + 1).reversed())
                sadatLine.addAll(subLine)
                subLine.clear()
            }
            else -> {
                nasserLine.add(nasser)
                sadatLine.add(kitkat)
            }
        }

        routes.add(nasserLine)
        routes.add(sadatLine)
        Log.d("MetroApp", "routes: $routes, sadatLine: $sadatLine")
    }
    private fun searchRoutesFromLine3ToLine1() {

        val startIndex = metroLine3.indexOf(start)
        val nasserIndexAtLine3 = metroLine3.indexOf(nasser)
        val kitKateIndex = metroLine3.indexOf(kitkat)

        val nasserLine = mutableListOf<String>()
        val kitKateLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        // Operations at Line 3
        when {
            startIndex < nasserIndexAtLine3 && startIndex < kitKateIndex -> {
                directionForFirstRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $nasser")
                transList1.add(nasser)

                directionForSecondRoute.append("$takeDirectionTo $adlyMansour $andChangeDirectionAt $kitkat $andChangeDirectionAt $cairoUniversity $takeDirectionTo $shobraElKheima")
                transList2.add(kitkat)
                transList2.add(cairoUniversity)

                nasserLine.addAll(metroLine3.subList(startIndex, nasserIndexAtLine3))
                kitKateLine.addAll(metroLine3.subList(startIndex, kitKateIndex))
            }
            startIndex > nasserIndexAtLine3 && startIndex > kitKateIndex -> {
                directionForFirstRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $nasser ")
                transList1.add(nasser)

                directionForSecondRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $kitkat $andChangeDirectionAt $cairoUniversity $takeDirectionTo $shobraElKheima")
                transList2.add(kitkat)
                transList2.add(cairoUniversity)

                subLine.addAll(metroLine3.subList(kitKateIndex + 1, startIndex + 1).reversed())
                kitKateLine.addAll(subLine)
                subLine.clear()

                subLine.addAll(metroLine3.subList(nasserIndexAtLine3 + 1, startIndex + 1).reversed())
                nasserLine.addAll(subLine)
            }
            else -> {
                // You are at Nasser or KitKate
                directionForFirstRoute.append("$youAreAt $nasser")
                directionForSecondRoute.append("$youAreAt $kitkat")
            }
        }

        // Operations at Line 2
        val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        kitKateLine.addAll(metroLine2.subList(sadatIndexAtLine2, cairoIndexAtLine2))

        subLine.addAll(cairoKitKateBranch.reversed())
        kitKateLine.addAll(subLine)
        subLine.clear()

        // Operations at Line 1
        val endIndex = metroLine1.indexOf(end)
        val nasserIndexAtLine1 = metroLine1.indexOf(nasser)
        val sadatIndexAtLine1 = metroLine1.indexOf(sadat)

        when {
            endIndex > nasserIndexAtLine1 && endIndex > sadatIndexAtLine1 -> {
                directionForFirstRoute.append("$thenTakeDirectionTo $elMarg")
                directionForSecondRoute.append("$thenTakeDirectionTo $elMarg")

                nasserLine.addAll(metroLine1.subList(nasserIndexAtLine1, endIndex + 1))
                kitKateLine.addAll(metroLine1.subList(sadatIndexAtLine1 + 1, endIndex + 1))
            }
            endIndex < nasserIndexAtLine1 && endIndex < sadatIndexAtLine1 -> {
                directionForFirstRoute.append("$thenTakeDirectionTo $helwan")
                directionForSecondRoute.append("$thenTakeDirectionTo $helwan")

                subLine.addAll(metroLine1.subList(endIndex, nasserIndexAtLine1 + 1).reversed())
                nasserLine.addAll(subLine)
                subLine.clear()

                subLine.addAll(metroLine1.subList(endIndex, sadatIndexAtLine1).reversed())
                kitKateLine.addAll(subLine)
                subLine.clear()
            }
            else -> {
                nasserLine.add(nasser)
                kitKateLine.add(kitkat)
            }
        }

        routes.add(nasserLine)
        routes.add(kitKateLine)
    }
    private fun searchRoutesFromLine1ToCairoKitKateBranch() {
        val startIndex = metroLine1.indexOf(start)
        val nasserIndexAtLine1 = metroLine1.indexOf(nasser)
        val sadatIndexAtLine1 = metroLine1.indexOf(sadat)

        val nasserLine = mutableListOf<String>()
        val sadatLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        // Operations at Line 1
        when {
            startIndex < nasserIndexAtLine1 && startIndex < sadatIndexAtLine1 -> {
                directionForFirstRoute.append("$takeDirectionTo $elMarg $andChangeDirectionAt $nasser ")
                transList1.add(nasser)
                directionForSecondRoute.append("$takeDirectionTo $elMarg $andChangeDirectionAt $sadat ")
                transList2.add(sadat)
                nasserLine.addAll(metroLine1.subList(startIndex, nasserIndexAtLine1))
                sadatLine.addAll(metroLine1.subList(startIndex, sadatIndexAtLine1))
            }
            startIndex > nasserIndexAtLine1 && startIndex > sadatIndexAtLine1 -> {
                directionForFirstRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $nasser ")
                transList1.add(nasser)
                directionForSecondRoute.append("$takeDirectionTo $helwan $andChangeDirectionAt $sadat ")
                transList2.add(sadat)

                subLine.addAll(metroLine1.subList(sadatIndexAtLine1 + 1, startIndex))
                sadatLine.addAll(subLine.reversed())
                subLine.clear()

                subLine.addAll(metroLine1.subList(nasserIndexAtLine1 + 1, startIndex))
                nasserLine.addAll(subLine.reversed())
            }
            startIndex == nasserIndexAtLine1 -> {
                directionForFirstRoute.append("$youAreAt $nasser")
                directionForSecondRoute.append("$youAreAt $sadat")
            }
            else -> {
                directionForFirstRoute.append("$youAreAt $start")
                nasserLine.addAll(metroLine1.subList(sadatIndexAtLine1, nasserIndexAtLine1))
                directionForSecondRoute.append("$youAreAt $sadat")
            }
        }

        // Operations at Line 2 for Sadat route
        val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        directionForSecondRoute.append("$thenTakeDirectionTo $elmounib $andChangeDirectionAt $cairoUniversity")
        transList2.add(cairoUniversity)
        sadatLine.addAll(metroLine2.subList(sadatIndexAtLine2, cairoIndexAtLine2))

        // Operations at Cairo KitKate branch for Sadat route
        val endIndexAtCairoKitKateBranch = cairoKitKateBranch.indexOf(end)
        val cairoIndexAtCairoKitKateBranch = cairoKitKateBranch.indexOf(cairoUniversity)
        subLine.addAll(cairoKitKateBranch.subList(cairoIndexAtCairoKitKateBranch, cairoIndexAtCairoKitKateBranch + 1).reversed())
        sadatLine.addAll(subLine)
        subLine.clear()

        // Operations at Line 3 for Nasser route
        directionForFirstRoute.append("$takeDirectionTo $roadElFargCorr $andChangeDirectionAt $kitkat ")
        transList1.add(kitkat)
        val nasserIndexAtLine3 = metroLine3.indexOf(nasser)
        val kitKateIndexAtLine3 = metroLine3.indexOf(kitkat)
        subLine.addAll(metroLine3.subList(kitKateIndexAtLine3 + 1, nasserIndexAtLine3 + 1).reversed())
        nasserLine.addAll(subLine)
        subLine.clear()

        val kitKateIndexAtCairoKitKateBranch = cairoKitKateBranch.indexOf(kitkat)

        // Operations at Cairo KitKate branch for Nasser route
        nasserLine.addAll(cairoKitKateBranch.subList(kitKateIndexAtCairoKitKateBranch, endIndexAtCairoKitKateBranch + 1))

        // Store the found routes in the routes list
        routes.add(nasserLine)
        routes.add(sadatLine)
    }
    private fun searchRoutesFromCairoKitKateBranchToLine1() {
        val startIndex = cairoKitKateBranch.indexOf(start)
        val cairoIndexAtKitKatCairoUniversityBranch = cairoKitKateBranch.indexOf(cairoUniversity)
        val kitKateIndexAtCairoBranch = cairoKitKateBranch.indexOf(kitkat)

        val nasserLine = mutableListOf<String>()
        val sadatLine = mutableListOf<String>()
        val subLine = mutableListOf<String>()

        // Operations at KitKat Cairo University line for Sadat route
        when {
            startIndex < cairoIndexAtKitKatCairoUniversityBranch -> {
                sadatLine.addAll(cairoKitKateBranch.subList(startIndex, cairoIndexAtKitKatCairoUniversityBranch + 1))
            }
            startIndex > cairoIndexAtKitKatCairoUniversityBranch -> {
                subLine.addAll(cairoKitKateBranch.subList(cairoIndexAtKitKatCairoUniversityBranch, startIndex + 1).reversed())
                sadatLine.addAll(subLine)
                subLine.clear()
            }
        }
        directionForSecondRoute.append("$andChangeDirectionAt $cairoUniversity ")
        transList2.add(cairoUniversity)

        // Operations at Line 2 for Sadat route
        val sadatIndexAtLine2 = metroLine2.indexOf(sadat)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        if (cairoIndexAtLine2 < sadatIndexAtLine2) {
            sadatLine.addAll(metroLine2.subList(cairoIndexAtLine2 + 1, sadatIndexAtLine2 + 1))
        } else {
            subLine.addAll(metroLine2.subList(sadatIndexAtLine2, cairoIndexAtLine2).reversed())
            sadatLine.addAll(subLine)
            subLine.clear()
        }
        directionForSecondRoute.append("$thenTakeDirectionTo $shobraElKheima $andChangeDirectionAt $sadat ")
        transList2.add(sadat)

        // Operations at Line 1 for Sadat route
        val sadatIndexAtLine1 = metroLine1.indexOf(sadat)
        val endIndexAtLine1 = metroLine1.indexOf(end)
        if (endIndexAtLine1 > sadatIndexAtLine1) {
            sadatLine.addAll(metroLine1.subList(sadatIndexAtLine1, endIndexAtLine1 + 1))
            directionForSecondRoute.append("$thenTakeDirectionTo $elMarg ")
        } else if (endIndexAtLine1 < sadatIndexAtLine1) {
            directionForSecondRoute.append("$thenTakeDirectionTo $helwan ")
            subLine.addAll(metroLine1.subList(endIndexAtLine1, sadatIndexAtLine1 + 1).reversed())
            sadatLine.addAll(subLine)
            subLine.clear()
        }

        // Operations at KitKat Cairo University line for Nasser route
        directionForFirstRoute.append("$andChangeDirectionAt $kitkat ")
        transList1.add(kitkat)
        if (startIndex < kitKateIndexAtCairoBranch) {
            nasserLine.addAll(cairoKitKateBranch.subList(startIndex, kitKateIndexAtCairoBranch + 1))
        } else if (startIndex > kitKateIndexAtCairoBranch) {
            subLine.addAll(cairoKitKateBranch.subList(kitKateIndexAtCairoBranch, startIndex + 1).reversed())
            nasserLine.addAll(subLine)
            subLine.clear()
        }

        // Operations at Line 3 for Nasser route
        directionForFirstRoute.append("$thenTakeDirectionTo $adlyMansour $andChangeDirectionAt $nasser ")
        transList1.add(nasser)
        val nasserIndexAtLine3 = metroLine3.indexOf(nasser)
        val kitKateIndexAtLine3 = metroLine3.indexOf(kitkat)

        nasserLine.addAll(metroLine3.subList(kitKateIndexAtLine3 + 1, nasserIndexAtLine3 + 1))

        // Operations at Line 1 for Nasser route
        val nasserIndexAtLine1 = metroLine1.indexOf(nasser)

        if (endIndexAtLine1 > nasserIndexAtLine1) {
            directionForFirstRoute.append("$thenTakeDirectionTo $elMarg ")
            nasserLine.addAll(metroLine1.subList(nasserIndexAtLine1, endIndexAtLine1 + 1))
        } else if (endIndexAtLine1 < nasserIndexAtLine1) {
            directionForFirstRoute.append("$thenTakeDirectionTo $helwan ")
            subLine.addAll(metroLine1.subList(endIndexAtLine1, nasserIndexAtLine1 + 1).reversed())
            nasserLine.addAll(subLine)
            subLine.clear()
        }

        routes.add(nasserLine)
        routes.add(sadatLine)
    }
    private fun searchRoutesFromCairoKitKateBranchToLine2() {
        val startIndex = cairoKitKateBranch.indexOf(start)
        val endIndex = metroLine2.indexOf(end)
        val attabaIndexAtLine2 = metroLine2.indexOf(attaba)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)
        val kitKateIndexAtCairoBranch = cairoKitKateBranch.indexOf(kitkat)
        val cairoUniversityIndexAtCairoBranch = cairoKitKateBranch.indexOf(cairoUniversity)
        val attabaIndexAtLine3 = metroLine3.indexOf(attaba)
        val kitKateIndexAtLine3 = metroLine3.indexOf(kitkat)

        val attabaLine = mutableListOf<String>()
        val cairoLine = mutableListOf<String>()
        var subLine = mutableListOf<String>()

        // Option 1: Change at Attaba
        directionForFirstRoute.append("${changeDirectionTo}  ${kitkat} ")
        transList1.add(kitkat)
        subLine = cairoKitKateBranch.subList(kitKateIndexAtCairoBranch, startIndex + 1).reversed().toMutableList()
        attabaLine.addAll(subLine)
        subLine.clear()

        attabaLine.addAll(metroLine3.subList(kitKateIndexAtLine3 + 1, attabaIndexAtLine3))
        directionForFirstRoute.append("${thenTakeDirectionTo} ${adlyMansour} ${andChangeDirectionAt} ${attaba} ")
        transList1.add(attaba)
        if (endIndex < attabaIndexAtLine2) {
            directionForFirstRoute.append("${thenTakeDirectionTo} ${shobraElKheima} ")
            subLine = metroLine2.subList(endIndex, attabaIndexAtLine2 + 1).reversed().toMutableList()
            attabaLine.addAll(subLine)
            subLine.clear()
        } else {
            directionForFirstRoute.append("${thenTakeDirectionTo} ${elmounib} ")
            attabaLine.addAll(metroLine2.subList(attabaIndexAtLine2, endIndex + 1))
        }

        // Option 2: Change at Cairo University
        directionForSecondRoute.append("${changeDirectionTo} ${cairoUniversity} ")
        transList2.add(cairoUniversity)
        cairoLine.addAll(cairoKitKateBranch.subList(startIndex, cairoUniversityIndexAtCairoBranch + 1))
        if (endIndex < cairoIndexAtLine2) {
            directionForSecondRoute.append("${thenTakeDirectionTo} ${shobraElKheima} ")
            subLine = metroLine2.subList(endIndex, cairoIndexAtLine2).reversed().toMutableList()
            cairoLine.addAll(subLine)
            subLine.clear()
        } else {
            directionForSecondRoute.append("${thenTakeDirectionTo} ${elmounib} ")
            cairoLine.addAll(metroLine2.subList(cairoIndexAtLine2 + 1, endIndex + 1))
        }

        routes.add(attabaLine)
        routes.add(cairoLine)
    }
    private fun searchRoutesFromLine2ToCairoKitKateBranch() {
        val startIndex = metroLine2.indexOf(start)
        val endIndex = cairoKitKateBranch.indexOf(end)
        val attabaIndexAtLine2 = metroLine2.indexOf(attaba)
        val cairoIndexAtLine2 = metroLine2.indexOf(cairoUniversity)

        val attabaLine = mutableListOf<String>()
        val cairoLine = mutableListOf<String>()
        var subLine = mutableListOf<String>()

        // Operations on Line 2
        when {
            startIndex > attabaIndexAtLine2 && startIndex > cairoIndexAtLine2 -> {
                directionForFirstRoute.append("${takeDirectionTo} ${shobraElKheima} ${andChangeDirectionAt} ${attaba} ")
                transList1.add(attaba)
                directionForSecondRoute.append("${takeDirectionTo} ${shobraElKheima} ${andChangeDirectionAt} ${cairoUniversity} ")
                transList2.add(cairoUniversity)
                subLine = metroLine2.subList(attabaIndexAtLine2 + 1, startIndex + 1).reversed().toMutableList()
                attabaLine.addAll(subLine)
                subLine.clear()
                subLine = metroLine2.subList(cairoIndexAtLine2 + 1, startIndex + 1).reversed().toMutableList()
                cairoLine.addAll(subLine)
            }
            startIndex < attabaIndexAtLine2 && startIndex < cairoIndexAtLine2 -> {
                directionForFirstRoute.append("${takeDirectionTo} ${elmounib} ${andChangeDirectionAt} ${attaba} ")
                transList1.add(attaba)
                directionForSecondRoute.append("${takeDirectionTo} ${elmounib} ${andChangeDirectionAt} ${cairoUniversity} ")
                transList2.add(cairoUniversity)
                attabaLine.addAll(metroLine2.subList(startIndex, attabaIndexAtLine2))
                cairoLine.addAll(metroLine2.subList(startIndex, cairoIndexAtLine2))
            }
            startIndex > attabaIndexAtLine2 && startIndex < cairoIndexAtLine2 -> {
                directionForFirstRoute.append("${takeDirectionTo} ${shobraElKheima} ${andChangeDirectionAt} ${attaba} ")
                transList1.add(attaba)
                directionForSecondRoute.append("${takeDirectionTo} ${elmounib} ${andChangeDirectionAt} ${cairoUniversity} ")
                transList2.add(cairoUniversity)
                subLine = metroLine2.subList(attabaIndexAtLine2 + 1, startIndex).reversed().toMutableList()
                attabaLine.addAll(subLine)
                subLine.clear()
                cairoLine.addAll(metroLine2.subList(startIndex, cairoIndexAtLine2))
            }
            else -> {
                directionForFirstRoute.append("${youAreAt} ${attaba} ")
                directionForSecondRoute.append("${youAreAt} ${cairoUniversity} ")
            }
        }

        // Operations on Line 3 with Attaba line
        directionForFirstRoute.append("${thenTakeDirectionTo} ${roadElFargCorr} $andChangeDirectionAt $kitkat ")
        transList1.add(kitkat)

        val attabaIndexAtLine3 = metroLine3.indexOf(attaba)
        val kitKateIndexAtLine3 = metroLine3.indexOf(kitkat)
        val kitKateIndexAtCairoBranch = cairoKitKateBranch.indexOf(kitkat)
        val cairoIndexAtCairoBranch = cairoKitKateBranch.indexOf(cairoUniversity)

        subLine = metroLine3.subList(kitKateIndexAtLine3 + 1, attabaIndexAtLine3 + 1).reversed().toMutableList()
        attabaLine.addAll(subLine)
        subLine.clear()
        attabaLine.addAll(cairoKitKateBranch.subList(kitKateIndexAtCairoBranch, endIndex + 1))

        // Operations on Cairo Branch with Cairo line
        directionForSecondRoute.append("${thenTakeDirectionTo} $kitkat ")
        subLine = cairoKitKateBranch.subList(endIndex, cairoIndexAtCairoBranch + 1).reversed().toMutableList()
        cairoLine.addAll(subLine)
        subLine.clear()

        routes.add(attabaLine)
        routes.add(cairoLine)
    }
    private fun searchRoutesFromLine3ToCairoKitKateBranch() {
        val combinedLine = mutableListOf<String>()
        val reversedLine3 = metroLine3.reversed()

        val kitKateIndex = reversedLine3.indexOf(kitkat)
        combinedLine.addAll(reversedLine3.subList(0, kitKateIndex))
        combinedLine.addAll(cairoKitKateBranch)

        println(combinedLine)
        routes.add(getSingleRoute(combinedLine, start, end))
    }
}