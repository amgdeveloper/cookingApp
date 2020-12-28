package com.amgdeveloper.cookingapp.model.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
) : Parcelable