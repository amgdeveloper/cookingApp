package com.amgdeveloper.cookingapp.model

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by amgdeveloper on 05/12/2020
 */
class CuisineRepository(activity: AppCompatActivity) {

    private var permissionChecker = PermissionChecker(activity)
    private var locationDataSource: PlayServicesLocationDataSource = PlayServicesLocationDataSource(activity)
    private var geocoder: Geocoder = Geocoder(activity)

    companion object {
        private const val DEFAULT_CUISINE = "italian"
        private const val DEFAULT_REGION = "IT"
    }

    suspend fun getCuisine(): String {
        val region = getLastLocation().toRegion()
        return getCuisineTypeFromRegion(region)
    }

    private suspend fun getLastLocation(): Location? {
        return if (permissionChecker.isCoarseLocationPermissionGranted()) {
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