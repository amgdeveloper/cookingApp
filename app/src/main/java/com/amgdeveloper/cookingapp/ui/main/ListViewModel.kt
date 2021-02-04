package com.amgdeveloper.cookingapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amgdeveloper.cookingapp.common.Event
import com.amgdeveloper.cookingapp.common.ScopedViewModel
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.usecases.GetRecipesByRegion
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * Created by amgdeveloper on 13/12/2020
 */
class ListViewModel(private val getRecipesByRegion: GetRecipesByRegion,
                    override val uiDispatcher: CoroutineDispatcher): ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val recipes: List<Recipe>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private val _navigation = MutableLiveData < Event<Recipe>>()
    val navigation :  LiveData<Event<Recipe>> =_navigation

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
            _model.value = UiModel.Content(getRecipesByRegion.invoke())
        }
    }

    fun onRecipeClicked(recipe: Recipe) {
        _navigation.value = Event(recipe)
    }
}