package com.amgdeveloper.cookingapp.view.list

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeClient
import com.amgdeveloper.cookingapp.network.RecipeRepository
import com.amgdeveloper.cookingapp.view.details.RecipeDetailsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {


    private val adapter = RecipeListAdapter(emptyList()) { showRecipeDetails(it) }

    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(activity as AppCompatActivity) }


    private fun showRecipeDetails(recipe: Recipe) {
        val intent = Intent(context, RecipeDetailsActivity::class.java)
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipe)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            adapter.recipes = recipeRepository.getRecipesByRegion()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        val recyclerView = binding.recipeListFragmentRv
        recyclerView.adapter = adapter
        return binding.root
    }
}