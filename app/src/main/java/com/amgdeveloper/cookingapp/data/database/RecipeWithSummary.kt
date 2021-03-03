package com.amgdeveloper.cookingapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by amgdeveloper on 26/02/2021
 */
data class RecipeWithSummary (
    @Embedded val recipe : Recipe,
            @Relation(
                    parentColumn = "id",
                    entityColumn = "recipeId"
            )
            val summary : RecipeSummary?
)