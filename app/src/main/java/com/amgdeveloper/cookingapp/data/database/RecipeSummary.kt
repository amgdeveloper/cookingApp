package com.amgdeveloper.cookingapp.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Recipe::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("recipeId"),
        onDelete = CASCADE)])


data class RecipeSummary(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val recipeId: Int,
        val summary: String,
        val title: String
)