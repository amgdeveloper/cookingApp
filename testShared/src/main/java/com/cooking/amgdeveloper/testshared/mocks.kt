package com.cooking.amgdeveloper.testshared

import com.amgdeveloper.domain.Recipe
import com.amgdeveloper.domain.RecipeSummary


val mockedRecipe = Recipe(
    0,
    "https://spoonacular.com/recipeImages/631747-312x231.jpg",
    "jpg",
    "Dutch Oven Paella",
    "Spanish",
    false)


val mockedRecipeSummary = RecipeSummary(
    0, 0, "This is the summary" +
            "of the recipe", "Dutch Oven Paella"
)