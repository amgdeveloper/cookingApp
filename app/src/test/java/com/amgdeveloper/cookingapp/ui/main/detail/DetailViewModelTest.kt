package com.amgdeveloper.cookingapp.ui.main.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.ui.detail.DetailViewModel
import com.amgdeveloper.usecases.GetRecipeById
import com.amgdeveloper.usecases.GetRecipeSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.cooking.amgdeveloper.testshared.mockedRecipeSummary
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
    lateinit var getRecipeById: GetRecipeById

    @Mock
    lateinit var  getRecipeSummary: GetRecipeSummary

    @Mock
    lateinit var  toggleRecipeFavorite: ToggleRecipeFavorite

    @Mock
    private lateinit var observer : Observer<DetailViewModel.RecipeWithSummary>

    private lateinit var vm : DetailViewModel

    @Before
    fun setup() {
        vm = DetailViewModel(getRecipeById, getRecipeSummary, toggleRecipeFavorite,
                Dispatchers.Unconfined, 5)
    }

    @Test
    fun observingGetsTheRecipe() {
        runBlocking {

            val recipe = mockedRecipe.copy(id = 5)
            val recipeSummary = mockedRecipeSummary.copy(recipeId = 5)
            val uiModel = mockedRecipeWithSummary.copy(recipeId = 5)

            whenever(getRecipeById.invoke(5)).thenReturn(recipe)
            whenever(getRecipeSummary.invoke(5)).thenReturn(recipeSummary)
            vm.model.observeForever(observer)

            verify(observer).onChanged(uiModel)
        }
    }

    @Test
    fun whenFavoriteClickedToggleMovieUseCaseIsInvoked(){
        runBlocking {
            val recipe = mockedRecipe.copy(id = 5)
            val recipeSummary = mockedRecipeSummary.copy(recipeId = 5)

            whenever(getRecipeById.invoke(5)).thenReturn(recipe)
            whenever(getRecipeSummary.invoke(5)).thenReturn(recipeSummary)
            whenever(toggleRecipeFavorite.invoke(recipe)).thenReturn(recipe.copy(favorite = !recipe.favorite))
            vm.model.observeForever(observer)
            vm.onFavoriteClicked()

            verify(toggleRecipeFavorite).invoke(recipe)

        }
    }

    private val mockedRecipeWithSummary = DetailViewModel.RecipeWithSummary(
            1,  "Dutch Oven Paella","This is the summary" +
            "of the recipe",  false,"https://spoonacular.com/recipeImages/631747-312x231.jpg"
    )
}