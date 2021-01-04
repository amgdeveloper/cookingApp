package com.amgdeveloper.cookingapp.model.database

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
    fun getRecipeSummary(id: Int): RecipeSummary

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeSummary(summary: RecipeSummary)

    @Query("UPDATE Recipe SET favorite = CASE WHEN :favorite THEN 1 ELSE 0 END" +
            " WHERE id == :id")
    fun markAsFavorite(id:Int, favorite:Boolean):Int
}