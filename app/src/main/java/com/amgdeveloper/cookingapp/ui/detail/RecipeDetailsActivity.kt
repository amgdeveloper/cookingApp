package com.amgdeveloper.cookingapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amgdeveloper.cookingapp.R
import com.amgdeveloper.cookingapp.common.fragmentExists
import com.amgdeveloper.cookingapp.model.database.Recipe

/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_RECIPE = "recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipe = intent.getParcelableExtra<Recipe>(EXTRA_RECIPE)
        if (recipe != null) {
            displayRecipeDetails(recipe)
        }
    }

    private fun displayRecipeDetails(recipe: Recipe) {
        if (!supportFragmentManager.fragmentExists(RecipeDetailsFragment.TAG)) {
            val fragment = RecipeDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_RECIPE, recipe)
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                    .replace(R.id.recipeDetailsActivityFl, fragment, RecipeDetailsFragment.TAG)
                    .commit()
        }
    }
}