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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeClient
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

    companion object{
        private const val DEFAULT_CUISINE = "italian"
        private const val DEFAULT_REGION = "IT"
    }


    private val adapter = RecipeListAdapter(emptyList()) { showRecipeDetails(it) }
    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                doRequestRandomRecipes(isGranted)
            }

    private lateinit var fusedLocationClient : FusedLocationProviderClient


    private fun showRecipeDetails(recipe: Recipe) {
        val intent = Intent(context, RecipeDetailsActivity::class.java)
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipe)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
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


    private fun doRequestRandomRecipes(isLocationGranted: Boolean) {
        lifecycleScope.launch {
            val map = mutableMapOf<String, String>()
            map["apiKey"] = BuildConfig.API_KEY
            map["cuisine"] = getCuisine(isLocationGranted)
            withContext(Dispatchers.IO) {
                val recipeResult = RecipeClient.service.getRandomRecipes(map)
                withContext(Dispatchers.Main) {
                    adapter.recipes = recipeResult.results
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCuisine(isLocationGranted: Boolean): String = suspendCancellableCoroutine { continuation ->
        var cuisine = DEFAULT_CUISINE
        if (isLocationGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener() { location ->
                location.result?.let {
                    cuisine = getCuisineTypeFromRegion(getRegionFromLocation(it))
                }
                continuation.resume(cuisine)
            }
        } else {
            continuation.resume(cuisine)
        }
    }


    private fun getRegionFromLocation(location: Location): String {
        val geocoder = Geocoder(context)
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return result.firstOrNull()?.countryCode ?:
        DEFAULT_REGION
    }

    private fun getCuisineTypeFromRegion(region: String): String {
        return when (region) {
            "IT" -> "Italian"
            "UK" -> "British"
            "ES" -> "Spanish"
            "FR" -> "French"
            "DE" -> "German"
            "GR" -> "Greek"
            "IE" -> "Irish"
            "CN" -> "Chinese"
            "JP" -> "Japanese"
            "IN" -> "Indian"
            "MX" -> "Mexican"
            else -> DEFAULT_CUISINE
        }
    }
}