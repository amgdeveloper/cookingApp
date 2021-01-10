package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository

/**
 * Created by amgdeveloper on 09/01/2021
 */
class ToggleRecipeFavorite(private val recipeRepository: RecipeRepository) {

    suspend fun invoke(id: Int, favorite: Boolean) {
        recipeRepository.markRecipeAsFavorite(id, favorite)
    }
}