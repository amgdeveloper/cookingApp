package com.amgdeveloper.cookingapp.model.server

import kotlin.Int

data class RecipeSummary(
        val id: Int,
        val summary: String,
        val title: String
)