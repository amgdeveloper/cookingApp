package com.amgdeveloper.cookingapp.di

import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by amgdeveloper on 19/01/2021
 */
@Module
class DataModule {

    @Provides
    fun recipeRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        cuisineRepository: CuisineRepository
    ): RecipeRepository = RecipeRepository(localDataSource, remoteDataSource, cuisineRepository)
}