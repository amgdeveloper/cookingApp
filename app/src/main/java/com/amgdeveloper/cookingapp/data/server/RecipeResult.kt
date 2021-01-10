package com.amgdeveloper.cookingapp.data.server

data class RecipeResult(
        val number: Int,
        val offset: Int,
        val results: List<Recipe>,
        val totalResults: Int
)