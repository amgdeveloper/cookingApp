package com.amgdeveloper.cookingapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.common.app
import com.amgdeveloper.cookingapp.common.getViewModel
import com.amgdeveloper.cookingapp.common.loadImage
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.model.database.Recipe

import com.amgdeveloper.cookingapp.model.server.RecipeRepository


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment() {

    private lateinit var recipe: Recipe
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var viewModel: DetailViewModel
    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(requireActivity().app) }

    companion object {
        val TAG: String = RecipeDetailsFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
            recipe.let {
                viewModel = getViewModel { DetailViewModel(recipeRepository, recipe.id) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        recipe.let {
            binding.fragmentRecipeDetailsToolbar.title = recipe.title
            context?.let {
                binding.fragmentRecipeDetailsHeaderIv.loadImage(recipe.image)
                viewModel.model.observe(viewLifecycleOwner, Observer {
                    binding.fragmentRecipeDetailsSummaryTv.text = it.summary
                })
            }
        }
        return binding.root
    }
}