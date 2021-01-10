package com.amgdeveloper.cookingapp.model

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.amgdeveloper.data.source.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by amgdeveloper on 09/12/2020
 */
class PlayServicesLocationDataSource(private val application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override suspend fun findLastRegion(): String? = suspendCancellableCoroutine { continuation ->
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        fusedLocationClient.lastLocation.addOnCompleteListener {
            continuation.resume(it.result.toRegion())
        }
    }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}