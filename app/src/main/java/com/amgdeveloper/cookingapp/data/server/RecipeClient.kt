package com.amgdeveloper.cookingapp.data.server

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by amgdeveloper on 18/11/2020
 */
object RecipeClient {

    //private val client = OkHttpClient.Builder().addInterceptor(getHttpLoginInterceptor()).build()

    private val retrofit  = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(client)
        .build()

    var service: RecipeService = retrofit.create(RecipeService::class.java)

    private fun getHttpLoginInterceptor() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        return interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
    }
}