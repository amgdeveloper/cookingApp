package com.amgdeveloper.domain

/**
 * Created by amgdeveloper on 09/01/2021
 */
data class RecipeSummary(
    val id: Int,
    val recipeId: Int,
    val summary: String,
    val title: String
)