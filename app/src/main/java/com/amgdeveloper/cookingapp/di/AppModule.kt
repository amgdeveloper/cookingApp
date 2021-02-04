package com.amgdeveloper.cookingapp.di

import android.app.Application
import androidx.room.Room
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.data.AndroidPermissionChecker
import com.amgdeveloper.cookingapp.data.PlayServicesLocationDataSource
import com.amgdeveloper.cookingapp.data.database.RecipeDatabase
import com.amgdeveloper.cookingapp.data.database.RoomDataSource
import com.amgdeveloper.cookingapp.data.server.SpoonacularDataSource
import com.amgdeveloper.data.PermissionChecker
import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.LocationDataSource
import com.amgdeveloper.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by amgdeveloper on 19/01/2021
 */
@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider():String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun databaseProvider(app: Application) =
        Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe-db").build()

    @Provides
    fun localDataSourceProvider(db: RecipeDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(@Named ("apiKey")apiKey: String): RemoteDataSource =
        SpoonacularDataSource(apiKey)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    fun cuisineRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker): CuisineRepository =
        CuisineRepository(locationDataSource, permissionChecker)

    @Provides
    fun coroutineDispatcher():CoroutineDispatcher = Dispatchers.Main
}