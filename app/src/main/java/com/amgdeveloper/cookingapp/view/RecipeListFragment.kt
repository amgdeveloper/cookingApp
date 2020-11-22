package com.amgdeveloper.cookingapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeClient
import com.amgdeveloper.cookingapp.view.details.RecipeDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {

    private val adapter = RecipeListAdapter(emptyList()) { showRecipeDetails(it) }

    private fun showRecipeDetails(recipe: Recipe) {
        val intent = Intent(context, RecipeDetailsActivity::class.java)
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipe)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val map = mutableMapOf<String, String>()
            map["apiKey"] = BuildConfig.API_KEY
            map["cuisine"] = "italian"    //search italian recipes by default
            withContext(Dispatchers.IO) {
                val recipeResult = RecipeClient.service.getRandomRecipes(map)
                withContext(Dispatchers.Main) {
                    adapter.recipes = recipeResult.results
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        val recyclerView = binding.recipeListFragmentRv
        recyclerView.adapter = adapter
        return binding.root
    }
}