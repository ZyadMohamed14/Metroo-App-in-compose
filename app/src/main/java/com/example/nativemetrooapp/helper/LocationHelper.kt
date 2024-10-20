package com.example.nativemetrooapp.helper

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class  LocationHelper @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) {
    @OptIn(ExperimentalCoroutinesApi::class)
     suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {

            return null
        }

        // Use suspendCancellableCoroutine to fetch the last known location
        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    // Check if the task was successful
                    if (isSuccessful) {
                        continuation.resume(result) // Resume coroutine with the location
                    } else {
                        continuation.resume(null) // Resume coroutine with null if not successful
                    }
                    return@suspendCancellableCoroutine
                }

                // Add listeners to handle success, failure, and cancellation
                addOnSuccessListener { location ->
                    continuation.resume(location) // Resume with the location
                }
                addOnFailureListener { exception ->
                    continuation.resumeWithException(exception) // Resume with exception
                }
                addOnCanceledListener {
                    continuation.cancel() // Cancel the coroutine
                }
            }
        }
    }







     fun checkLocationPermissions(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // If both permissions are already granted, return true
        if (fineLocationPermission && coarseLocationPermission) {
            return true
        }

        // If permissions are not granted, request them
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                application.applicationContext as Activity ,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Show rationale to the user, if needed
            // You can implement this as a dialog or a snackbar
        }

        // Request permissions
        ActivityCompat.requestPermissions(
            application.applicationContext as Activity,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_CODE_LOCATION_PERMISSION
        )

        return false // Permissions will be checked again after the user responds
    }


     fun getAddressFromLatLng(lat: Double, lng: Double): String? {
        return try {
            val geocoder = Geocoder(application, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0) // Get the full address line
            } else {
                null // No address found
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log the exception
            null // Return null in case of an error
        }
    }
     fun getLatLngFromAddress(address: String): Pair<Double, Double>? {
        return try {
            val geocoder = Geocoder(application, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(address, 1) // Get lat/lng from address
            if (!addresses.isNullOrEmpty()) {
                val location = addresses[0] // Get the first address found
                Pair(location.latitude, location.longitude) // Return lat and lng
            } else {
                null // No location found
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log the exception
            null // Return null in case of an error
        }
    }
    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1001
    }

}
