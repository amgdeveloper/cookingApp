package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.cooking.amgdeveloper.testshared.mockedRecipe
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
class GetRecipeByIdTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipeById: GetRecipeById

    @Before
    fun setUp(){
        getRecipeById = GetRecipeById(recipeRepository)
    }

    @Test
    fun invokeCallsRecipeRepository(){
        runBlocking {
            val recipe = mockedRecipe.copy(id = 2)
            whenever(recipeRepository.getRecipeById(2)).thenReturn(recipe)

            val result = getRecipeById.invoke(2)

            Assert.assertEquals(recipe,result)
        }
    }
}