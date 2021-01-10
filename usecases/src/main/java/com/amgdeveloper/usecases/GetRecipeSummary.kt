package com.amgdeveloper.usecases

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.domain.RecipeSummary

/**
 * Created by amgdeveloper on 09/01/2021
 */
class GetRecipeSummary(private val recipeRepository: RecipeRepository) {

    suspend fun invoke(id: Int): RecipeSummary? = recipeRepository.getRecipeSummary(id)
}