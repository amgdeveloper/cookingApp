package com.amgdeveloper.cookingapp.ui.detail

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.usecases.GetRecipeById
import com.amgdeveloper.usecases.GetRecipeByIdWithSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by amgdeveloper on 20/01/2021
 */

@Module
class RecipeDetailFragmentModule(private val recipeId : Int){

    @Provides
    fun getRecipesByIdProvider(recipeRepository: RecipeRepository): GetRecipeById =
        GetRecipeById(recipeRepository)

    @Provides
    fun getRecipeSummaryProvider(recipeRepository: RecipeRepository): GetRecipeByIdWithSummary =
        GetRecipeByIdWithSummary(recipeRepository)

    @Provides
    fun toggleRecipeFavoriteProvider(recipeRepository: RecipeRepository): ToggleRecipeFavorite =
        ToggleRecipeFavorite(recipeRepository)

    @Provides
    fun detailViewModelProvider(
            getRecipeByIdWithSummary: GetRecipeByIdWithSummary,
            toggleRecipeFavorite: ToggleRecipeFavorite,
            coroutineDispatcher: CoroutineDispatcher
    ): DetailViewModel = DetailViewModel(getRecipeByIdWithSummary, toggleRecipeFavorite,
            coroutineDispatcher, recipeId)

}

@Subcomponent (modules = [RecipeDetailFragmentModule::class])
interface RecipeDetailFragmentComponent {

    val detailViewModel: DetailViewModel
}