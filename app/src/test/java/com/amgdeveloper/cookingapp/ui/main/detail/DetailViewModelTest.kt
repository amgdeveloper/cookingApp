package com.amgdeveloper.cookingapp.ui.main.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.ui.detail.DetailViewModel
import com.amgdeveloper.usecases.GetRecipeByIdWithSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
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
 * Created by amgdeveloper on 09/02/2021
 */
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get : Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var  getRecipeByIdWithSummary: GetRecipeByIdWithSummary

    @Mock
    lateinit var  toggleRecipeFavorite: ToggleRecipeFavorite

    @Mock
    private lateinit var observer : Observer<DetailViewModel.UIModel>

    private lateinit var vm : DetailViewModel

    @Before
    fun setup() {
        vm = DetailViewModel(getRecipeByIdWithSummary, toggleRecipeFavorite,
                Dispatchers.Unconfined, 5)
    }

    @Test
    fun observingGetsTheRecipeSummary() {
        runBlocking {

            val recipe = mockedRecipe.copy(id = 5)
            val uiModel = DetailViewModel.UIModel(mockedRecipe.copy(id = 5))

            whenever(getRecipeByIdWithSummary.invoke(5)).thenReturn(recipe)
            vm.model.observeForever(observer)

            verify(observer).onChanged(uiModel)
        }
    }

    @Test
    fun whenFavoriteClickedToggleMovieUseCaseIsInvoked(){
        runBlocking {
            val recipe = mockedRecipe.copy(id = 5)

            whenever(getRecipeByIdWithSummary.invoke(5)).thenReturn(recipe)
            whenever(toggleRecipeFavorite.invoke(recipe)).thenReturn(recipe.copy(favorite = !recipe.favorite))
            vm.model.observeForever(observer)
            vm.onFavoriteClicked()

            verify(toggleRecipeFavorite).invoke(recipe)
        }
    }
}