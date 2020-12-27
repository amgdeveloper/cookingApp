package com.amgdeveloper.cookingapp.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amgdeveloper.cookingapp.common.Event
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
        object RequestLocationPermission : UiModel()
    }

    private val _navigation = MutableLiveData < Event<Recipe>>()
    val navigation :  LiveData<Event<Recipe >> =_navigation

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }


    private fun refresh(){
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(recipeRepository.getRecipesByRegion())
        }
    }

    override fun onCleared() {
        destroyScope()
    }

    fun onRecipeClicked(recipe: Recipe) {
        _navigation.value = Event(recipe)
    }
}