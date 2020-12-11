package com.amgdeveloper.cookingapp.network

import androidx.appcompat.app.AppCompatActivity
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.model.CuisineRepository
import com.amgdeveloper.cookingapp.model.Recipe

/**
 * Created by amgdeveloper on 05/12/2020
 */
class RecipeRepository(activity: AppCompatActivity) {

    private val cuisineRepository = CuisineRepository(activity)

    suspend fun getRecipesByRegion(): List<Recipe> {
        val cuisine = cuisineRepository.getCuisine()
        val map = mutableMapOf<String, String>()
        map["apiKey"] = BuildConfig.API_KEY
        map["cuisine"] = cuisine
        val recipeResult = RecipeClient.service.getRandomRecipes(map)
        return recipeResult.results
    }
}