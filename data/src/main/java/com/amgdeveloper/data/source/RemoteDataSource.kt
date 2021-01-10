package com.amgdeveloper.data.source

import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary

/**
 * Created by amgdeveloper on 09/01/2021
 */
interface RemoteDataSource {
    suspend fun getRecipesByCuisine(cuisine: String): List<Recipe>
    suspend fun getRecipeSummary(id: Int): RecipeSummary
}