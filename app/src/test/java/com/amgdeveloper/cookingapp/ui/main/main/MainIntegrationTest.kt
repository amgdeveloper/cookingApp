package com.amgdeveloper.cookingapp.ui.main.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.ui.main.*
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 10/02/2021
 */
@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest {

    @get : Rule
    val rule = InstantTaskExecutorRule()

    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private val observer : Observer<ListViewModel.UiModel> = mock()
    private lateinit var vm: ListViewModel

    @Before
    fun setup() {
        vm = component.plus(RecipeListFragmentModule()).listViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
    }


    @Test
    fun `data is loaded from server when local source is empty`() {
        runBlocking {
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(ListViewModel.UiModel.Content(defaultFakeRecipes))
        }
    }

    @Test
    fun `data is loaded from local source when available`(){
        val fakeRecipes = listOf(mockedRecipe.copy(7), mockedRecipe.copy(8))
        localDataSource.recipes = fakeRecipes
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(ListViewModel.UiModel.Content(fakeRecipes))
    }
}