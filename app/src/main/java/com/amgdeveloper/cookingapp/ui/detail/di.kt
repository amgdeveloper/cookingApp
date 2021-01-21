package com.amgdeveloper.cookingapp.ui.detail

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.usecases.GetRecipeById
import com.amgdeveloper.usecases.GetRecipeSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by amgdeveloper on 20/01/2021
 */

@Module
class RecipeDetailFragmentModule(private val recipeId : Int){

    @Provides
    fun getRecipesByIdProvider(recipeRepository: RecipeRepository): GetRecipeById =
        GetRecipeById(recipeRepository)

    @Provides
    fun getRecipeSummaryProvider(recipeRepository: RecipeRepository): GetRecipeSummary =
        GetRecipeSummary(recipeRepository)

    @Provides
    fun toggleRecipeFavoriteProvider(recipeRepository: RecipeRepository): ToggleRecipeFavorite =
        ToggleRecipeFavorite(recipeRepository)

    @Provides
    fun detailViewModelProvider(
        getRecipeById: GetRecipeById,
        getRecipeSummary: GetRecipeSummary,
        toggleRecipeFavorite: ToggleRecipeFavorite
    ): DetailViewModel = DetailViewModel(getRecipeById, getRecipeSummary, toggleRecipeFavorite, recipeId)

}

@Subcomponent (modules = [RecipeDetailFragmentModule::class])
interface RecipeDetailFragmentComponent {

    val detailViewModel: DetailViewModel
}