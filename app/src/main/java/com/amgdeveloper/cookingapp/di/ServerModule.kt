package com.amgdeveloper.cookingapp.di

import com.amgdeveloper.cookingapp.data.server.Spoonacular
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by amgdeveloper on 16/02/2021
 */
@Module
class ServerModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() = "https://api.spoonacular.com/"


    @Provides
    fun spoonacularProvider(@Named("baseUrl") baseUrl : String): Spoonacular = Spoonacular(baseUrl)
}
