package com.amgdeveloper.cookingapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amgdeveloper.cookingapp.common.loadImage
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.model.Recipe


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment(), DetailPresenter.View{

    private lateinit var recipe: Recipe
    private lateinit var binding : FragmentRecipeDetailsBinding
    private lateinit var presenter : DetailPresenter

    companion object {
        val TAG: String = RecipeDetailsFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
            recipe.let {
                presenter = DetailPresenter(this, recipe.id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        presenter.onCreateView()
        recipe.let {
            binding.fragmentRecipeDetailsTitleTv.text = recipe.title
            context?.let {
                binding.fragmentRecipeDetailsHeaderIv.loadImage(recipe.image)
            }
        }
        presenter.onCreateView()
        return binding.root
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setSummary(summary: String) {
        binding.fragmentRecipeDetailsSummaryTv.text = summary
    }
}