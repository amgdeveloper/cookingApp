package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.domain.Recipe

/**
 * Created by amgdeveloper on 09/01/2021
 */
class ToggleRecipeFavorite(private val recipeRepository: RecipeRepository) {

    suspend fun invoke(recipe: Recipe): Recipe = with(recipe) {
        copy(favorite = !favorite).also { recipeRepository.update(it) }
    }
}