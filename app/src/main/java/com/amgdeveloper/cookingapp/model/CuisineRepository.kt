package com.amgdeveloper.cookingapp.model

import android.Manifest
import android.app.Application
import android.location.Geocoder
import android.location.Location

/**
 * Created by amgdeveloper on 05/12/2020
 */
class CuisineRepository(application: Application) {

    private var permissionChecker = PermissionChecker(application, Manifest.permission.ACCESS_COARSE_LOCATION)
    private var locationDataSource: PlayServicesLocationDataSource = PlayServicesLocationDataSource(application)
    private var geocoder: Geocoder = Geocoder(application)

    companion object {
        private const val DEFAULT_CUISINE = "italian"
        private const val DEFAULT_REGION = "IT"
    }

    suspend fun getCuisine(): String {
        val region = getLastLocation().toRegion()
        return getCuisineTypeFromRegion(region)
    }

    private suspend fun getLastLocation(): Location? {
        return if (permissionChecker.check()) {
            locationDataSource.getLastLocation()
        } else {
            null
        }
    }


    private fun Location?.toRegion(): String {
        val list = this?.let { geocoder.getFromLocation(latitude, longitude, 1) }
        return list?.firstOrNull()?.countryCode ?: DEFAULT_REGION

    }

    private fun getCuisineTypeFromRegion(region: String): String {
        return when (region) {
            "IT" -> "Italian"
            "UK" -> "British"
            "ES" -> "Spanish"
            "FR" -> "French"
            "DE" -> "German"
            "GR" -> "Greek"
            "IE" -> "Irish"
            "CN" -> "Chinese"
            "JP" -> "Japanese"
            "IN" -> "Indian"
            "MX" -> "Mexican"
            else -> DEFAULT_CUISINE
        }
    }
}