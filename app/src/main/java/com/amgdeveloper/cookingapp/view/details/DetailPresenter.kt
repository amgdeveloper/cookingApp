package com.amgdeveloper.cookingapp.view.details

import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.common.Scope
import com.amgdeveloper.cookingapp.network.RecipeClient
import kotlinx.coroutines.launch

class DetailPresenter(var view: View?, val id: Int) : Scope by Scope.Impl() {

    interface View {
        fun setSummary(summary: String)
    }

    fun onCreateView() {
        initScope()
        getSummary(id)
    }

    fun onDestroy(){
        view = null
        destroyScope()
    }

    private fun getSummary(id: Int) {
        launch {
            val summary = RecipeClient.service.getRecipeSummary(id, BuildConfig.API_KEY)
            view?.setSummary(summary.summary)
        }
    }
}