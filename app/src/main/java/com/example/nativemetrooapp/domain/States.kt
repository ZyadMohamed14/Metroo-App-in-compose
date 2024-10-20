package com.example.nativemetrooapp.domain

sealed class AppResources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Idle<T> : AppResources<T>() // New Idle state
    class Success<T>(data: T) : AppResources<T>(data)
    class Error<T>(message: String, data: T? = null) : AppResources<T>(data, message)
    class Loading<T> : AppResources<T>()
}
