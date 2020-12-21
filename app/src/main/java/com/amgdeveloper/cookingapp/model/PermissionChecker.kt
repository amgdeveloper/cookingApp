package com.amgdeveloper.cookingapp.model

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by amgdeveloper on 05/12/2020
 */
class PermissionChecker(private val activity: Activity, private val permission: String) {

    suspend fun isCoarseLocationPermissionGranted(): Boolean =
            suspendCancellableCoroutine { continuation ->
                Dexter
                        .withActivity(activity)
                        .withPermission(permission)
                        .withListener(object : BasePermissionListener() {
                            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                                continuation.resume(true)
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                                continuation.resume(false)
                            }
                        }
                        ).check()
            }
}
