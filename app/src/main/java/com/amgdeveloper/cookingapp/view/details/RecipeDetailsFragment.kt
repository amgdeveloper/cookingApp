package com.amgdeveloper.cookingapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment() {

    private lateinit var recipe: Recipe
    private lateinit var binding : FragmentRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
            recipe.let {
                this.getSummary(recipe.id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        recipe.let {
            binding.fragmentRecipeDetailsTitleTv.text = recipe.title
            context?.let {
                Glide.with(it)
                    .load(recipe.image)
                    .into(binding.fragmentRecipeDetailsHeaderIv)
            }
        }
        return binding.root
    }

    private fun getSummary(id: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val summary = RecipeClient.service.getRecipeSummary(id, BuildConfig.API_KEY)
                withContext(Dispatchers.Main) {
                    binding.fragmentRecipeDetailsSummaryTv.text = summary.summary
                }
            }
        }
    }
}