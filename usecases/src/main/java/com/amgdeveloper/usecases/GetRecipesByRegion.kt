package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.domain.Recipe

/**
 * Created by amgdeveloper on 09/01/2021
 */
class GetRecipesByRegion(private val recipeRepository: RecipeRepository) {

    suspend fun invoke(): List<Recipe> = recipeRepository.getRecipesByRegion()
}