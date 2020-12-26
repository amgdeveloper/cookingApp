package com.amgdeveloper.cookingapp.common

import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

fun FragmentManager.fragmentExists(TAG: String): Boolean {
    return findFragmentByTag(TAG) != null
}