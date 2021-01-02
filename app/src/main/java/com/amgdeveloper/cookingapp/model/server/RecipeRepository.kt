package com.amgdeveloper.cookingapp.model.server

import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.common.RecipesApp
import com.amgdeveloper.cookingapp.model.CuisineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Int
import com.amgdeveloper.cookingapp.model.database.Recipe as DbRecipe
import com.amgdeveloper.cookingapp.model.database.RecipeSummary as DbRecipeSummary
import com.amgdeveloper.cookingapp.model.server.Recipe as ServerRecipe


/**
 * Created by amgdeveloper on 05/12/2020
 */
class RecipeRepository(application: RecipesApp) {

    private val apiKey = BuildConfig.API_KEY
    private val cuisineRepository = CuisineRepository(application)
    private val db = application.db

    suspend fun getRecipesByRegion(): List<DbRecipe> = withContext(Dispatchers.IO) {
        val cuisine = cuisineRepository.getCuisine()
        var recipes: List<DbRecipe>
        with(db.recipeDao()) {
            recipes = getRecipesByCuisine(cuisine)
            if (recipes.isEmpty()) {
                val map = mutableMapOf<String, String>()
                map["apiKey"] = apiKey
                map["cuisine"] = cuisine
                val recipeResult = RecipeClient.service.getRandomRecipes(map)
                val serverRecipes :List<ServerRecipe> = recipeResult.results
                insertRecipes(serverRecipes.map { it.convertToDbRecipe(cuisine) })
                recipes = getRecipesByCuisine(cuisine)
            }
            recipes
        }
    }

    suspend fun getRecipeSummary(id : Int): DbRecipeSummary = withContext(Dispatchers.IO) {
        with(db.recipeDao()) {
            var summary : DbRecipeSummary = getRecipeSummary(id)
            if (summary == null) {
                summary = RecipeClient.service.getRecipeSummary(id, apiKey).convertToDbRecipeSummary()
                insertRecipeSummary(summary)
                summary = getRecipeSummary(id)
            }
            summary
        }
    }

    private fun ServerRecipe.convertToDbRecipe(cuisine: String) = DbRecipe(
            id, image, imageType, title, cuisine, false)

    private fun RecipeSummary.convertToDbRecipeSummary() = DbRecipeSummary(0, id, summary, title)
}