package com.amgdeveloper.repository

import com.amgdeveloper.data.PermissionChecker
import com.amgdeveloper.data.PermissionChecker.Permission.COARSE_LOCATION
import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.source.LocationDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 26/01/2021
 */
@RunWith(MockitoJUnitRunner::class)
class CuisineRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var cuisineRepository: CuisineRepository

    @Before
    fun setUp() {
        cuisineRepository = CuisineRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun returnsDefaultCuisineWhenCoarsePermissionNotGranted() {
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

            val cuisine = cuisineRepository.getCuisineAccordingCurrentLocation()

            Assert.assertEquals(CuisineRepository.DEFAULT_CUISINE,cuisine)
        }
    }

    @Test
    fun returnsCuisineFromLocationDataSourceWhenPermissionGranted(){
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("ES")

            val cuisine = cuisineRepository.getCuisineAccordingCurrentLocation()

            Assert.assertEquals("Spanish",cuisine)
        }
    }
}