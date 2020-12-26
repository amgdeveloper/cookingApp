package com.amgdeveloper.cookingapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.amgdeveloper.cookingapp.common.loadImage
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.model.Recipe


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment() {

    private lateinit var recipe: Recipe
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        val TAG: String = RecipeDetailsFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
            recipe.let {
                viewModel = ViewModelProvider(this,DetailViewModel.DetailViewModelFactory(recipe.id)).get()
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
                binding.fragmentRecipeDetailsHeaderIv.loadImage(recipe.image)
                viewModel.model.observe(viewLifecycleOwner, Observer {
                    binding.fragmentRecipeDetailsSummaryTv.text = it.summary
                })
            }
        }
        return binding.root
    }
}