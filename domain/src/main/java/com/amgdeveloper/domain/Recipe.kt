package com.amgdeveloper.domain

/**
 * Created by amgdeveloper on 09/01/2021
 */
data class Recipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String,
    val cuisine: String,
    val favorite: Boolean
)