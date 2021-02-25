package com.amgdeveloper.cookingapp.common

import android.app.Application
import com.amgdeveloper.cookingapp.di.CookingComponent
import com.amgdeveloper.cookingapp.di.DaggerCookingComponent

/**
 * Created by amgdeveloper on 28/12/2020
 */

open class RecipesApp : Application() {

    lateinit var component : CookingComponent

    override fun onCreate() {
        super.onCreate()

        component = initCookingComponent()
    }

    open fun initCookingComponent() = DaggerCookingComponent.factory().create(this)
}