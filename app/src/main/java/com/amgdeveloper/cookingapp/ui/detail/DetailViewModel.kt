package com.amgdeveloper.cookingapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amgdeveloper.cookingapp.common.ScopedViewModel
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.usecases.GetRecipeByIdWithSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
        private val getRecipeByIdWithSummary: GetRecipeByIdWithSummary,
        private val toggleRecipeFavorite: ToggleRecipeFavorite,
        override val uiDispatcher: CoroutineDispatcher,
        private val recipeId: Int) : ScopedViewModel(uiDispatcher) {

    data class UIModel(val recipe : Recipe)
    private lateinit var recipe : Recipe

    private val _model = MutableLiveData<UIModel>()
    val model: LiveData<UIModel>
        get() {
            if (_model.value == null) getSummary(recipeId)
            return _model
        }

    private fun getSummary(id: Int) {
        launch {
            recipe = getRecipeByIdWithSummary.invoke(id)
            _model.value = UIModel(recipe)
        }
    }

    fun onFavoriteClicked() {
        launch {
            _model.value?.let {
                recipe = toggleRecipeFavorite.invoke(recipe)
                _model.value = UIModel(recipe)
            }
        }
    }
}