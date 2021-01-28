package com.amgdeveloper.repository

import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.RemoteDataSource
import com.amgdeveloper.domain.Recipe
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals as assertEquals

/**
 * Created by amgdeveloper on 27/01/2021
 */
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var cuisineRepository: CuisineRepository

    private lateinit var recipeRepository: RecipeRepository


    @Before
    fun setUp() {
        recipeRepository = RecipeRepository(localDataSource, remoteDataSource, cuisineRepository)
    }


    @Test
    fun getRecipesByRegionGetsFromLocalDataSourceFirst(){
        runBlocking {

            val localRecipes = listOf(mockedRecipe.copy(5))
            whenever(cuisineRepository.getCuisineAccordingCurrentLocation()).thenReturn("Spanish")
            whenever(localDataSource.getRecipesByCuisine("Spanish")).thenReturn(localRecipes)

            val result = recipeRepository.getRecipesByRegion()

            assertEquals(localRecipes,result)
        }
    }

    @Test
    fun getRecipesFromRegionSavesRemoteDataToLocal(){
        runBlocking {

            val cuisine = "Spanish"
            val remoteRecipes = listOf(mockedRecipe.copy(5))

            whenever(cuisineRepository.getCuisineAccordingCurrentLocation()).thenReturn(cuisine)
            whenever(localDataSource.getRecipesByCuisine(cuisine)).thenReturn(emptyList())
            whenever(remoteDataSource.getRecipesByCuisine(cuisine)).thenReturn(remoteRecipes)

            recipeRepository.getRecipesByRegion()

            verify(localDataSource).saveRecipes(remoteRecipes)
        }
    }

    @Test
    fun getRecipeByIdCallsLocalDataSource() {
        runBlocking {

            val recipe = mockedRecipe.copy(id = 99)
            whenever(localDataSource.getRecipeById(99)).thenReturn(recipe)

            val result = recipeRepository.getRecipeById(99)

            assertEquals(recipe, result)
        }
    }

    private val mockedRecipe = Recipe(
        0,
        "https://spoonacular.com/recipeImages/631747-312x231.jpg",
        "jpg",
        "Dutch Oven Paella",
        "Spanish",
        false
    )
}