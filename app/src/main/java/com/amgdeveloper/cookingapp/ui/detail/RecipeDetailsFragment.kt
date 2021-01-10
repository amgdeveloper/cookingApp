package com.amgdeveloper.cookingapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.data.AndroidPermissionChecker
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.R
import com.amgdeveloper.cookingapp.common.app
import com.amgdeveloper.cookingapp.common.getViewModel
import com.amgdeveloper.cookingapp.common.loadImage
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeDetailsBinding
import com.amgdeveloper.cookingapp.data.PlayServicesLocationDataSource
import com.amgdeveloper.cookingapp.data.database.RoomDataSource
import com.amgdeveloper.cookingapp.data.server.SpoonacularDataSource
import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.data.repository.RecipeRepository
import com.amgdeveloper.usecases.GetRecipeById
import com.amgdeveloper.usecases.GetRecipeSummary
import com.amgdeveloper.usecases.ToggleRecipeFavorite


/**
 * Created by amgdeveloper on 22/11/2020
 */
class RecipeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        val TAG: String = RecipeDetailsFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val recipeId = it.getInt(RecipeDetailsActivity.EXTRA_RECIPE_ID)

            val cuisineRepository = CuisineRepository(
                PlayServicesLocationDataSource(requireActivity().application),
                AndroidPermissionChecker(requireActivity().application))

            val recipeRepository = RecipeRepository(
                RoomDataSource(requireActivity().app.db),
                SpoonacularDataSource(BuildConfig.API_KEY), cuisineRepository
            )

            viewModel = getViewModel {
                DetailViewModel(
                    GetRecipeById(recipeRepository),
                    GetRecipeSummary(recipeRepository),
                    ToggleRecipeFavorite(recipeRepository), recipeId
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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