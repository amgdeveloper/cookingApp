package com.amgdeveloper.cookingapp.model.database

import com.amgdeveloper.cookingapp.model.toDatabaseRecipe
import com.amgdeveloper.cookingapp.model.toDatabaseRecipeSummary
import com.amgdeveloper.cookingapp.model.toDomainRecipe
import com.amgdeveloper.cookingapp.model.toDomainRecipeSummary
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

    override suspend fun markRecipeAsFavorite(id: Int, favorite: Boolean) {
        withContext(Dispatchers.IO){
            recipeDao.markAsFavorite(id,favorite)
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe =
        withContext(Dispatchers.IO) {
            recipeDao.getRecipeById(id).toDomainRecipe()
        }
}