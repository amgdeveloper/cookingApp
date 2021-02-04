package com.amgdeveloper.cookingapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amgdeveloper.cookingapp.common.ScopedViewModel
import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.usecases.GetRecipeById
import com.amgdeveloper.usecases.GetRecipeSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getRecipeById: GetRecipeById,
    private val getRecipeSummary: GetRecipeSummary,
    private val toggleRecipeFavorite: ToggleRecipeFavorite,
    override val uiDispatcher: CoroutineDispatcher,
    private val recipeId: Int) : ScopedViewModel(uiDispatcher) {

    data class UiModel(val title: String, val summary: String, var favorite: Boolean, val image: String)

    private lateinit var recipe : Recipe

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getSummary(recipeId)
            return _model
        }

    private fun getSummary(id: Int) {
        launch {
            recipe = getRecipeById.invoke(id)
            recipe.let {
                _model.value = getRecipeSummary.invoke(id)?.let { it1 ->
                    UiModel(
                        recipe.title,
                        it1.summary,
                        recipe.favorite,
                        recipe.image
                    )
                }
            }
        }
    }

    fun onFavoriteClicked() {
        launch {
            _model.value?.let {
                _model.value = UiModel(it.title, it.summary, !it.favorite, it.image)
                toggleRecipeFavorite.invoke(recipe.id, !it.favorite)
            }
        }
    }
}