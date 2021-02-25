package com.amgdeveloper.cookingapp

import com.amgdeveloper.cookingapp.common.RecipesApp
import com.amgdeveloper.cookingapp.di.CookingComponent

/**
 * Created by amgdeveloper on 17/02/2021
 */
class TestApplication : RecipesApp() {

    override fun initCookingComponent() = DaggerUITestComponent.factory().create(this)
}