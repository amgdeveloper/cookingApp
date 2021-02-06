package com.amgdeveloper.data.repository

import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary
import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.RemoteDataSource

/**
 * Created by amgdeveloper on 09/01/2021
 */
class RecipeRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val cuisineRepository: CuisineRepository) {


    suspend fun getRecipesByRegion(): List<Recipe> {
        val cuisine = cuisineRepository.getCuisineAccordingCurrentLocation()
        var recipes = localDataSource.getRecipesByCuisine(cuisine)
        if (recipes.isEmpty()) {
            recipes = remoteDataSource.getRecipesByCuisine(cuisine)
            localDataSource.saveRecipes(recipes)
            recipes = localDataSource.getRecipesByCuisine(cuisine)
        }
        return recipes
    }

    suspend fun getRecipeById(id: Int): Recipe {
        return localDataSource.getRecipeById(id)
    }

    suspend fun getRecipeSummary(id: Int): RecipeSummary? {
        val localSummary = localDataSource.getRecipeSummary(id)
       return if (localSummary == null) {
           val summary = remoteDataSource.getRecipeSummary(id)
           localDataSource.saveRecipeSummary(summary)
           localDataSource.getRecipeSummary(id)
       } else {
           localSummary
       }
    }

    suspend fun update(recipe : Recipe) {
        return localDataSource.update(recipe)
    }
}