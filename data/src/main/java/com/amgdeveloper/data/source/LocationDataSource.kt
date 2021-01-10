package com.amgdeveloper.data.source

/**
 * Created by amgdeveloper on 09/01/2021
 */
interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
