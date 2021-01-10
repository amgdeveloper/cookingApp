package com.amgdeveloper.data.source

import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary

/**
 * Created by amgdeveloper on 09/01/2021
 */
interface LocalDataSource {

    suspend fun getRecipesByCuisine(cuisine: String): List<Recipe>
    suspend fun saveRecipes(recipes: List<Recipe>)
    suspend fun getRecipeSummary(id: Int): RecipeSummary?
    suspend fun saveRecipeSummary(summary: RecipeSummary)
    suspend fun markRecipeAsFavorite(id: Int, favorite: Boolean)
    suspend fun getRecipeById(id: Int): Recipe
}