package com.amgdeveloper.cookingapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by amgdeveloper on 28/12/2020
 */

@Database(entities = [Recipe::class],version = 1)
abstract class RecipeDatabase : RoomDatabase(){

    abstract fun recipeDao() : RecipeDao

}