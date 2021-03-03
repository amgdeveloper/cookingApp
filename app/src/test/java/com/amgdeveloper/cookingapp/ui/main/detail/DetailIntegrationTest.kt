package com.amgdeveloper.cookingapp.ui.main.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.ui.detail.DetailViewModel
import com.amgdeveloper.cookingapp.ui.detail.RecipeDetailFragmentModule
import com.amgdeveloper.cookingapp.ui.main.*
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 15/02/2021
 */
@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTest {

    @get : Rule
    val rule = InstantTaskExecutorRule()

    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private val observer : Observer<DetailViewModel.UIModel> = mock()
    private lateinit var vm: DetailViewModel

    @Before
    fun setup() {
        vm = component.plus(RecipeDetailFragmentModule(1)).detailViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
        remoteDataSource = component.remoteDataSource as FakeRemoteDataSource
    }

    @Test
    fun `data summary is loaded from server when local source is empty`(){
        runBlocking {
            localDataSource.recipes = defaultFakeRecipes
            val recipeWithSummary = DetailViewModel.UIModel(mockedRecipe.copy(id = 1))

            vm.model.observeForever(observer)

            verify(observer).onChanged(recipeWithSummary)
        }
    }

    @Test
    fun `data summary is loaded from local source when available`(){
        runBlocking {
            localDataSource.recipes = defaultFakeRecipes
            localDataSource.recipeSummary = defaultRecipeSummary
            val recipeWithSummary = DetailViewModel.UIModel(mockedRecipe.copy(id = 1))

            vm.model.observeForever(observer)

            verify(observer).onChanged(recipeWithSummary)
        }
    }

    @Test
    fun `favorite is updated in local data source`(){
        localDataSource.recipes = defaultFakeRecipes
        vm.model.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            Assert.assertTrue(localDataSource.getRecipeById(1).favorite)
        }
    }
}