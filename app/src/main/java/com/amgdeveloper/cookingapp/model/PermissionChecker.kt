package com.amgdeveloper.cookingapp.model

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by amgdeveloper on 05/12/2020
 */
class PermissionChecker(private val activity: AppCompatActivity) {

    suspend fun isCoarseLocationPermissionGranted(): Boolean = suspendCancellableCoroutine { continuation ->
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            continuation.resume(isGranted)
        }.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}