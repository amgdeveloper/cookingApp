package com.amgdeveloper.cookingapp.network

import RecipeSummary
import com.amgdeveloper.cookingapp.model.RecipeResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by amgdeveloper on 18/11/2020
 */

interface RecipeService {
    @GET("recipes/complexSearch")
    suspend fun getRandomRecipes(@QueryMap options  :Map <String, String>): RecipeResult

    @GET("recipes/{id}/summary")
    suspend fun getRecipeSummary(@Path("id") id: Int, @Query("apiKey") apiKey: String): RecipeSummary
}
