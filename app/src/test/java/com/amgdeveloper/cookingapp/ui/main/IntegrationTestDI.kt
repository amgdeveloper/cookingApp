package com.amgdeveloper.cookingapp.ui.main

import com.amgdeveloper.cookingapp.di.CookingComponent
import com.amgdeveloper.cookingapp.di.DataModule
import com.amgdeveloper.data.PermissionChecker
import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.source.LocalDataSource
import com.amgdeveloper.data.source.LocationDataSource
import com.amgdeveloper.data.source.RemoteDataSource
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary
import com.cooking.amgdeveloper.testshared.mockedRecipe
import com.cooking.amgdeveloper.testshared.mockedRecipeSummary
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by amgdeveloper on 10/02/2021
 */

@Singleton
@Component(modules = [TestAppModule::class, DataModule::class])
interface TestComponent : CookingComponent {

    val localDataSource: LocalDataSource
    val remoteDataSource: RemoteDataSource

    @Component.Factory
    interface FactoryTest {
        fun create(): TestComponent
    }
}


@Module
class TestAppModule {

    @Provides
    fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    @Singleton
    @Provides
    fun cuisineRepositoryProvider(): CuisineRepository = CuisineRepository(
        fakeLocationDataSourceProvider(), fakePermissionCheckerProvider()
    )

    @Singleton
    @Provides
    fun localDataSourceProvider(): LocalDataSource = FakeLocalDataSource()

    @Singleton
    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = FakeRemoteDataSource()

    @Singleton
    @Provides
    fun fakeLocationDataSourceProvider(): LocationDataSource = FakeLocationDataSource()

    @Singleton
    @Provides
    fun fakePermissionCheckerProvider(): PermissionChecker = FakePermissionChecker()
}

class FakeLocalDataSource : LocalDataSource {

    var recipes: List<Recipe> = emptyList()
    var recipeSummary : RecipeSummary? = null

    override suspend fun getRecipesByCuisine(cuisine: String): List<Recipe> = recipes

    override suspend fun saveRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
    }

    override suspend fun saveRecipeSummary(summary: RecipeSummary) {
        recipeSummary = summary
    }

    override suspend fun update(recipe: Recipe) {
        recipes = recipes.filterNot { it.id == recipe.id } + recipe
    }

    override suspend fun getRecipeById(id: Int): Recipe = recipes.first { it.id == id }
}

class FakeRemoteDataSource : RemoteDataSource {
    private var recipes = defaultFakeRecipes
    private var recipeSummary = defaultRecipeSummary

    override suspend fun getRecipesByCuisine(cuisine: String): List<Recipe> = recipes

    override suspend fun getRecipeSummary(id: Int): RecipeSummary {
        return recipeSummary
    }
}

class FakeLocationDataSource : LocationDataSource {
    private var location = "IT"

    override suspend fun findLastRegion(): String {
        return location
    }
}


class FakePermissionChecker : PermissionChecker {
    private var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean {
        return permissionGranted
    }
}

val defaultRecipeSummary = mockedRecipeSummary.copy(id = 1)

val defaultFakeRecipes = listOf(
    mockedRecipe.copy(1),
    mockedRecipe.copy(2),
    mockedRecipe.copy(3),
    mockedRecipe.copy(4),
    mockedRecipe.copy(5)
)