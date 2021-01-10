package com.amgdeveloper.data.repository

import com.amgdeveloper.data.PermissionChecker
import com.amgdeveloper.data.PermissionChecker.Permission
import com.amgdeveloper.data.source.LocationDataSource

/**
 * Created by amgdeveloper on 09/01/2021
 */

class CuisineRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker) {

    companion object {
        const val DEFAULT_CUISINE = "italian"
        private const val DEFAULT_REGION = "IT"
    }

    suspend fun getCuisineAccordingCurrentLocation(): String {
        var region = DEFAULT_REGION
        if (permissionChecker.check(Permission.COARSE_LOCATION)) {
            region = locationDataSource.findLastRegion() ?: DEFAULT_REGION
        }
        return getCuisineTypeFromRegion(region)
    }
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
        else -> CuisineRepository.DEFAULT_CUISINE
    }
}

