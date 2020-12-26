package com.amgdeveloper.cookingapp.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
        Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

fun FragmentManager.fragmentExists(TAG: String): Boolean {
    return findFragmentByTag(TAG) != null
}