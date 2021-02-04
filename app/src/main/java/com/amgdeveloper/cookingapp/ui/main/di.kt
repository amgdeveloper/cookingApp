package com.amgdeveloper.cookingapp.ui.main

import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.usecases.GetRecipesByRegion
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by amgdeveloper on 20/01/2021
 */

@Module
class RecipeListFragmentModule {

    @Provides
    fun listViewModelProvider(getRecipesByRegion: GetRecipesByRegion,
                              coroutineDispatcher: CoroutineDispatcher): ListViewModel =
            ListViewModel(getRecipesByRegion, coroutineDispatcher)

    @Provides
    fun getRecipesByRegionProvider(recipeRepository: RecipeRepository): GetRecipesByRegion =
            GetRecipesByRegion(recipeRepository)
}

@Subcomponent(modules = [RecipeListFragmentModule::class])
interface RecipeListFragmentComponent {

    val listViewModel: ListViewModel
}
