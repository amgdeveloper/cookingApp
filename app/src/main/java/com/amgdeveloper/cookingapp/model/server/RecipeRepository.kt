package com.amgdeveloper.cookingapp.model.server

import android.app.Application
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.model.CuisineRepository

/**
 * Created by amgdeveloper on 05/12/2020
 */
class RecipeRepository(application: Application) {

    private val cuisineRepository = CuisineRepository(application)

    suspend fun getRecipesByRegion(): List<Recipe> {
        val cuisine = cuisineRepository.getCuisine()
        val map = mutableMapOf<String, String>()
        map["apiKey"] = BuildConfig.API_KEY
        map["cuisine"] = cuisine
        val recipeResult = RecipeClient.service.getRandomRecipes(map)
        return recipeResult.results
    }
}