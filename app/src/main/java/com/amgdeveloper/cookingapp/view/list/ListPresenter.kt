package com.amgdeveloper.cookingapp.view.list

import android.util.Log
import com.amgdeveloper.cookingapp.common.Scope
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeRepository
import kotlinx.coroutines.launch

/**
 * Created by amgdeveloper on 13/12/2020
 */
class ListPresenter(val recipeRepository: RecipeRepository )  : Scope by Scope.Impl() {

    interface View{
        fun showProgress()
        fun hideProgress()
        fun updateData(list: List<Recipe>)
    }

    private  var view : View? = null

    fun onCreate(view :View){
        initScope()
        launch {
            view.showProgress()
            Log.e("TAG","PROBLEM HERE")
            view.updateData(recipeRepository.getRecipesByRegion())
            view.hideProgress()
        }

    }

    fun onDestroy(){
        view = null
        destroyScope()
    }
}