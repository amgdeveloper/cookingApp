package com.amgdeveloper.data

/**
 * Created by amgdeveloper on 09/01/2021
 */
interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}
