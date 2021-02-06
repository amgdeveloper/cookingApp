package com.amgdeveloper.cookingapp.data.database

import androidx.room.*

/**
 * Created by amgdeveloper on 28/12/2020
 */

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe WHERE cuisine =:cuisine")
    fun getRecipesByCuisine(cuisine: String): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM RecipeSummary WHERE recipeId =:id")
    fun getRecipeSummary(id: Int): RecipeSummary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeSummary(summary: RecipeSummary)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecipe(recipe : Recipe)

    @Query("SELECT * FROM Recipe WHERE id = :id")
    fun getRecipeById(id : Int) : Recipe
}