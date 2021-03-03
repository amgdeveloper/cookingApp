package com.amgdeveloper.data.source

import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary

/**
 * Created by amgdeveloper on 09/01/2021
 */
interface LocalDataSource {

    suspend fun getRecipesByCuisine(cuisine: String): List<Recipe>
    suspend fun saveRecipes(recipes: List<Recipe>)
    suspend fun saveRecipeSummary(summary: RecipeSummary)
    suspend fun update(recipe : Recipe)
    suspend fun getRecipeById(id: Int): Recipe
}