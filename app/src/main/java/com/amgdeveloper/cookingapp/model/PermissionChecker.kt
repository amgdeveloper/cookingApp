package com.amgdeveloper.cookingapp.model

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by amgdeveloper on 05/12/2020
 */
class PermissionChecker(private val application: Application, private val permission: String) {

    fun check(): Boolean {
        return ContextCompat.checkSelfPermission(application, permission) == PackageManager.PERMISSION_GRANTED
    }
}
