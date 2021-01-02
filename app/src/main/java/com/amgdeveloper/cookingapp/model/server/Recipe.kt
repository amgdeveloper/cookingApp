package com.amgdeveloper.cookingapp.model.server


/**
 * Created by amgdeveloper on 02/01/2020
 */

data class Recipe(
        val id: Int,
        val image: String,
        val imageType: String,
        val title: String,
        val cuisine: String,
        val favorite: Boolean
)