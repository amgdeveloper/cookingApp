package com.amgdeveloper.cookingapp.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * Created by amgdeveloper on 30/01/2021
 */
abstract class ScopedViewModel : ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}