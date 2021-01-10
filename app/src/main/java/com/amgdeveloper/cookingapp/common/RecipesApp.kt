package com.amgdeveloper.cookingapp.common

import android.app.Application
import androidx.room.Room
import com.amgdeveloper.cookingapp.data.database.RecipeDatabase

/**
 * Created by amgdeveloper on 28/12/2020
 */

class RecipesApp : Application() {

    lateinit var db : RecipeDatabase
    private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this,RecipeDatabase::class.java,"recipe-db").build()
    }
}