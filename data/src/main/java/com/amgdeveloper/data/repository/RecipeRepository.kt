package com.amgdeveloper.data.repository

import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.RemoteDataSource
import com.amgdeveloper.domain.Recipe

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

    suspend fun getRecipeWithSummary(id: Int): Recipe {
        val recipe = localDataSource.getRecipeById(id)
        return if (recipe.summary == null) {
            val summary = remoteDataSource.getRecipeSummary(id)
            localDataSource.saveRecipeSummary(summary)
            localDataSource.getRecipeById(id)
        } else {
            recipe
        }
    }

    suspend fun update(recipe : Recipe) {
        return localDataSource.update(recipe)
    }
}