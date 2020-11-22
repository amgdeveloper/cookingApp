package com.amgdeveloper.cookingapp.network

import com.amgdeveloper.cookingapp.model.RecipeResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by amgdeveloper on 18/11/2020
 */

interface RecipeService {
    @GET("recipes/complexSearch")
    suspend fun getRandomRecipes(@QueryMap options  :Map <String, String>): RecipeResult
}
