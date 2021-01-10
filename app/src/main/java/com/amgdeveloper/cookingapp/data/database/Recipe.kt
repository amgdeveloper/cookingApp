package com.amgdeveloper.cookingapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by amgdeveloper on 28/12/2020
 */

@Entity
data class Recipe(
        @PrimaryKey(autoGenerate = false) val id: Int,
        val image: String,
        val imageType: String,
        val title: String,
        val cuisine: String,
        val favorite: Boolean
)