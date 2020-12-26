package com.amgdeveloper.cookingapp.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amgdeveloper.cookingapp.common.Scope
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeRepository
import kotlinx.coroutines.launch

/**
 * Created by amgdeveloper on 13/12/2020
 */
class ListViewModel(private val recipeRepository: RecipeRepository) : ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val recipes: List<Recipe>) : UiModel()
        class Navigation(val recipe: Recipe) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }


    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(recipeRepository.getRecipesByRegion())
        }

    }

    override fun onCleared() {
        destroyScope()
    }

    fun onRecipeClicked(recipe: Recipe) {
        _model.value = UiModel.Navigation(recipe)
    }

    @Suppress("UNCHECKED_CAST")
    class ListViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ListViewModel(repository) as T
    }

}