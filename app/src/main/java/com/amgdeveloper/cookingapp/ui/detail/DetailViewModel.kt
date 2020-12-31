package com.amgdeveloper.cookingapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amgdeveloper.cookingapp.common.Scope
import com.amgdeveloper.cookingapp.model.server.RecipeRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val recipeRepository: RecipeRepository, val id: Int) : Scope by Scope.Impl(), ViewModel() {

    class UiModel(val summary: String)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getSummary(id)
            return _model
        }

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    private fun getSummary(id: Int) {
        launch {
            _model.value = UiModel(recipeRepository.getRecipeSummary(id).summary)
        }
    }
}