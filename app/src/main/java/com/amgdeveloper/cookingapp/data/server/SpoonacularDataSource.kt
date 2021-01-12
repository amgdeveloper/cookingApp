package com.amgdeveloper.cookingapp.data.server

import com.amgdeveloper.cookingapp.data.toDomainRecipe
import com.amgdeveloper.cookingapp.data.toDomainRecipeSummary
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary
import com.amgdeveloper.data.source.RemoteDataSource

/**
 * Created by amgdeveloper on 09/01/2021
 */
class SpoonacularDataSource(private val apiKey: String) : RemoteDataSource {

    override suspend fun getRecipesByCuisine(cuisine: String): List<Recipe> {
        val map = mutableMapOf<String, String>()
        map["apiKey"] = apiKey
        map["cuisine"] = cuisine
        return Spoonacular.service.getRandomRecipes(map).results.map { it.toDomainRecipe(cuisine) }
    }

    override suspend fun getRecipeSummary(id: Int): RecipeSummary {
        return Spoonacular.service.getRecipeSummary(id, apiKey).toDomainRecipeSummary()
    }
}