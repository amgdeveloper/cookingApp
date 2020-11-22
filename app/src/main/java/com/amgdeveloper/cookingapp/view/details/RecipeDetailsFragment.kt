package com.amgdeveloper.cookingapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.bumptech.glide.Glide


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment() {

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentRecipeDetailsBinding.inflate(inflater, container, false)

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

}