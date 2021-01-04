package com.amgdeveloper.cookingapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amgdeveloper.cookingapp.R
import com.amgdeveloper.cookingapp.common.fragmentExists

/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_RECIPE_ID = "recipe_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeId = intent.getIntExtra(EXTRA_RECIPE_ID,-1)
        if (recipeId != -1) {
            displayRecipeDetails(recipeId)
        }
    }

    private fun displayRecipeDetails(recipeId: Int) {
        if (!supportFragmentManager.fragmentExists(RecipeDetailsFragment.TAG)) {
            val fragment = RecipeDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_RECIPE_ID, recipeId)
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                    .replace(R.id.recipeDetailsActivityFl, fragment, RecipeDetailsFragment.TAG)
                    .commit()
        }
    }
}