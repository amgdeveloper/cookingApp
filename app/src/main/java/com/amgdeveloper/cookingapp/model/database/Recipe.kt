package com.amgdeveloper.cookingapp.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by amgdeveloper on 28/12/2020
 */

@Parcelize
@Entity
data class Recipe(
        @PrimaryKey(autoGenerate = false) val id: Int,
        val image: String,
        val imageType: String,
        val title: String,
        val cuisine: String,
        val favorite: Boolean
) : Parcelable