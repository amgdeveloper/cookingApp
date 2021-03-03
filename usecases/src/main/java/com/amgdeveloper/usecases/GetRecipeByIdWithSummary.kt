package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.domain.Recipe

/**
 * Created by amgdeveloper on 09/01/2021
 */
class GetRecipeByIdWithSummary(private val recipeRepository: RecipeRepository) {

    suspend fun invoke(id: Int): Recipe = recipeRepository.getRecipeWithSummary(id)
}