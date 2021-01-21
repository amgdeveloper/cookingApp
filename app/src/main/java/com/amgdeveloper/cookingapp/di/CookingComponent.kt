package com.amgdeveloper.cookingapp.di

import android.app.Application
import com.amgdeveloper.cookingapp.ui.detail.RecipeDetailFragmentComponent
import com.amgdeveloper.cookingapp.ui.detail.RecipeDetailFragmentModule
import com.amgdeveloper.cookingapp.ui.main.RecipeListFragmentComponent
import com.amgdeveloper.cookingapp.ui.main.RecipeListFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by amgdeveloper on 19/01/2021
 */
@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface CookingComponent {


    fun plus(module : RecipeListFragmentModule) : RecipeListFragmentComponent

    fun plus (module : RecipeDetailFragmentModule) : RecipeDetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): CookingComponent
    }
}