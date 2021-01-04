package com.amgdeveloper.cookingapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.R
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

    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var viewModel: DetailViewModel
    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(requireActivity().app) }

    companion object {
        val TAG: String = RecipeDetailsFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val recipe: Recipe = it.getParcelable(RecipeDetailsActivity.EXTRA_RECIPE)!!
            recipe.let {
                viewModel = getViewModel { DetailViewModel(recipeRepository, recipe) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        binding.favoriteFab.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        context?.let {
            viewModel.model.observe(viewLifecycleOwner, Observer {
                updateUi(it)
            })
        }
        return binding.root
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(binding) {
        toolbar.title = model.title
        headerIv.loadImage(model.image)
        summaryTv.text = model.summary
        val icon = if (model.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        favoriteFab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, icon) })
    }
}