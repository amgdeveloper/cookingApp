package com.amgdeveloper.cookingapp.data

import com.amgdeveloper.cookingapp.data.database.Recipe as DatabaseRecipe
import com.amgdeveloper.cookingapp.data.database.RecipeSummary as DatabaseRecipeSummary
import com.amgdeveloper.cookingapp.data.server.Recipe as ServerRecipe
import com.amgdeveloper.cookingapp.data.server.RecipeSummary as ServerRecipeSummary
import com.amgdeveloper.domain.Recipe as DomainRecipe
import com.amgdeveloper.domain.RecipeSummary as DomainRecipeSummary

/**
 * Created by amgdeveloper on 09/01/2021
 */

fun DomainRecipe.toDatabaseRecipe(): DatabaseRecipe =
    DatabaseRecipe(id, image, imageType, title, cuisine, favorite)

fun DatabaseRecipe.toDomainRecipe(): DomainRecipe =
    DomainRecipe(id, image, imageType, title, cuisine, favorite)

fun ServerRecipe.toDomainRecipe(cuisine : String):DomainRecipe =
    DomainRecipe(id,image,imageType,title,cuisine,false)

fun DatabaseRecipeSummary.toDomainRecipeSummary(): DomainRecipeSummary =
        DomainRecipeSummary(id, recipeId, summary, title)

fun DomainRecipeSummary.toDatabaseRecipeSummary(): DatabaseRecipeSummary =
    DatabaseRecipeSummary(id, recipeId, summary, title)

fun ServerRecipeSummary.toDomainRecipeSummary(): DomainRecipeSummary =
        DomainRecipeSummary(0, id, summary, title)

