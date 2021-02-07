package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 07/02/2021
 */
@RunWith(MockitoJUnitRunner::class)
class ToggleRecipeFavoriteTest {


    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var toggleRecipeFavorite: ToggleRecipeFavorite

    @Before
    fun setUp(){
        toggleRecipeFavorite = ToggleRecipeFavorite(recipeRepository)
    }

    @Test
    fun invokeCallsRecipeRepository(){
        runBlocking {
            val recipe = mockedRecipe.copy(id = 7)

            val result = toggleRecipeFavorite.invoke(recipe)

            verify(recipeRepository).update(result)
        }
    }

    @Test
    fun favoriteRecipeBecomesUnfavorite() {
        runBlocking {
            val recipe = mockedRecipe.copy(favorite = true)

            val result = toggleRecipeFavorite.invoke(recipe)

            Assert.assertFalse(result.favorite)
        }
    }

    @Test
    fun favoriteRecipeBecomesFavorite() {
        runBlocking {
            val recipe = mockedRecipe.copy(favorite = false)

            val result = toggleRecipeFavorite.invoke(recipe)

            Assert.assertTrue(result.favorite)
        }
    }
}