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
class GetRecipeByIdWithSummaryTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipeByIdWithSummary: GetRecipeByIdWithSummary

    @Before
    fun setup() {
        getRecipeByIdWithSummary = GetRecipeByIdWithSummary(recipeRepository)
    }


    @Test
    fun invokeCallsRecipeRepository() {
        runBlocking {

            val recipeSummary = mockedRecipe.copy(id = 1)

            whenever(recipeRepository.getRecipeWithSummary(1)).thenReturn(recipeSummary)

            val result = getRecipeByIdWithSummary.invoke(1)

            Assert.assertEquals(recipeSummary, result)
        }
    }
}