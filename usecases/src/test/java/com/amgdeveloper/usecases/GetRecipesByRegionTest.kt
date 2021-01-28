package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by amgdeveloper on 28/01/2021
 */
@RunWith(MockitoJUnitRunner::class)
class GetRecipesByRegionTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipesByRegion: GetRecipesByRegion

    @Before
    fun setUp(){
        getRecipesByRegion = GetRecipesByRegion(recipeRepository)
    }

    @Test
    fun invokeCallsRecipeRepository(){
        runBlocking {

            val recipes = listOf(mockedRecipe.copy(id = 2))

            whenever(recipeRepository.getRecipesByRegion()).thenReturn(recipes)

            val result = getRecipesByRegion.invoke()

            Assert.assertEquals(recipes,result)
        }
    }
}