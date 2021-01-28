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
class GetRecipeSummaryTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipeSummary: GetRecipeSummary

    @Before
    fun setup() {
        getRecipeSummary = GetRecipeSummary(recipeRepository)
    }


    @Test
    fun invokeCallsRecipeRepository() {
        runBlocking {

            val recipeSummary = mockedRecipeSummary.copy(recipeId = 1)

            whenever(recipeRepository.getRecipeSummary(1)).thenReturn(recipeSummary)

            val result = getRecipeSummary.invoke(1)

            Assert.assertEquals(recipeSummary, result)
        }
    }
}