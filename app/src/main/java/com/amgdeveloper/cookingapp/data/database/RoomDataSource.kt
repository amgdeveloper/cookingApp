package com.amgdeveloper.cookingapp.data.database

import com.amgdeveloper.cookingapp.data.toDatabaseRecipe
import com.amgdeveloper.cookingapp.data.toDatabaseRecipeSummary
import com.amgdeveloper.cookingapp.data.toDomainRecipe
import com.amgdeveloper.cookingapp.data.toDomainRecipeSummary
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.amgdeveloper.data.source.LocalDataSource

/**
 * Created by amgdeveloper on 09/01/2021
 */
class RoomDataSource (db : RecipeDatabase): LocalDataSource {

    private val recipeDao = db.recipeDao()
    override suspend fun getRecipesByCuisine(cuisine: String): List<Recipe> =
        withContext(Dispatchers.IO){
            recipeDao.getRecipesByCuisine(cuisine).map { it.toDomainRecipe() }
        }

    override suspend fun saveRecipes(recipes: List<Recipe>) {
        withContext(Dispatchers.IO){
            recipeDao.insertRecipes(recipes.map { it.toDatabaseRecipe() })
        }
    }

    override suspend fun getRecipeSummary(id: Int): RecipeSummary? =
        withContext(Dispatchers.IO) {
            recipeDao.getRecipeSummary(id)?.toDomainRecipeSummary()
        }

    override suspend fun saveRecipeSummary(summary: RecipeSummary) {
        withContext(Dispatchers.IO){
            recipeDao.insertRecipeSummary(summary.toDatabaseRecipeSummary())
        }
    }

    override suspend fun update(recipe: Recipe) {
        withContext(Dispatchers.IO){
            recipeDao.updateRecipe(recipe.toDatabaseRecipe())
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe =
        withContext(Dispatchers.IO) {
            recipeDao.getRecipeById(id).toDomainRecipe()
        }
}