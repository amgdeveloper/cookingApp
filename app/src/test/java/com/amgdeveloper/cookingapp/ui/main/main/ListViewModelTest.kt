package com.amgdeveloper.cookingapp.ui.main.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.ui.main.ListViewModel
import com.amgdeveloper.usecases.GetRecipesByRegion
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 02/02/2021
 */
@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {

    @get : Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getRecipesByRegion: GetRecipesByRegion

    @Mock
    lateinit var observer: Observer<ListViewModel.UiModel>

    private lateinit var vm: ListViewModel


    @Before
    fun setUp() {
        vm = ListViewModel(getRecipesByRegion, Dispatchers.Unconfined)
    }

    @Test
    fun observingLiveDataLaunchesLocationPermissionRequest() {
        vm.model.observeForever(observer)

        verify(observer).onChanged(ListViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun afterRequestingThePermissionLoadingIsShown(){
        runBlocking {

            val recipes = listOf(mockedRecipe.copy(id = 1))
            whenever(getRecipesByRegion.invoke()).thenReturn(recipes)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(ListViewModel.UiModel.Loading)
        }
    }

    @Test
    fun afterRequestingThePermissionGetRecipesByRegionIsCalled(){
        runBlocking {

            val recipes = listOf(mockedRecipe.copy(id = 1))
            whenever(getRecipesByRegion.invoke()).thenReturn(recipes)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(ListViewModel.UiModel.Content(recipes))
        }
    }
}