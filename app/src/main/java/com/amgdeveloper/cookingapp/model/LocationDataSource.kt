package com.amgdeveloper.cookingapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by amgdeveloper on 09/12/2020
 */
interface LocationDataSource {
    suspend fun getLastLocation(): Location?
}


class PlayServicesLocationDataSource(private val context: Context) : LocationDataSource {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location? = suspendCancellableCoroutine { continuation ->
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnCompleteListener {
            continuation.resume(it.result)
        }
    }
}