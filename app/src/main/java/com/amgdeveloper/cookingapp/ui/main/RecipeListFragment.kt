package com.amgdeveloper.cookingapp.ui.main

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.AndroidPermissionChecker
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.CoarseLocationPermissionRequester
import com.amgdeveloper.cookingapp.common.app
import com.amgdeveloper.cookingapp.common.getViewModel
import com.amgdeveloper.cookingapp.common.startActivity
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.model.PlayServicesLocationDataSource
import com.amgdeveloper.cookingapp.model.database.RoomDataSource
import com.amgdeveloper.cookingapp.model.server.SpoonacularDataSource
import com.amgdeveloper.cookingapp.ui.detail.RecipeDetailsActivity
import com.amgdeveloper.cookingapp.ui.main.ListViewModel.UiModel.*
import com.amgdeveloper.data.repository.CuisineRepository
import com.amgdeveloper.usecases.GetRecipesByRegion

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {

    private lateinit var viewModel : ListViewModel
    private lateinit var adapter: RecipeListAdapter
    private lateinit var progressDialog: ProgressBar
    private lateinit var coarsePermissionRequester :CoarseLocationPermissionRequester
    companion object {
        val TAG: String = RecipeListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
          coarsePermissionRequester = CoarseLocationPermissionRequester(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        val recyclerView = binding.recipeListFragmentRv
        progressDialog = binding.recipeListFragmentPb


        viewModel = getViewModel { ListViewModel(createGetRecipesByRegionUseCase()) }
        adapter = RecipeListAdapter(emptyList(), viewModel::onRecipeClicked)
        recyclerView.adapter = adapter
        viewModel.model.observe(viewLifecycleOwner, Observer(this::updateUi))

        viewModel.navigation.observe(viewLifecycleOwner,Observer {event ->
            event.getContentIfNotHandled()?.let {
                context?.startActivity<RecipeDetailsActivity> {
                    putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, it.id)
                }
            }
        })
        return binding.root
    }

    private fun updateUi(model : ListViewModel.UiModel){
        progressDialog.visibility = if (model == Loading)View.VISIBLE else View.GONE
        when (model ){
            is Content -> {
                adapter.recipes = model.recipes
                adapter.notifyDataSetChanged()
            }
            RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }

    private fun createGetRecipesByRegionUseCase(): GetRecipesByRegion {
        val localDataSource = RoomDataSource(requireActivity().app.db)
        val remoteDataSource = SpoonacularDataSource(BuildConfig.API_KEY)

        val cuisineRepository = CuisineRepository(
            PlayServicesLocationDataSource(
                requireActivity().application
            ),
            AndroidPermissionChecker(requireActivity().application)
        )
        val recipeRepository: com.amgdeveloper.data.repository.RecipeRepository =
            com.amgdeveloper.data.repository.RecipeRepository(
                localDataSource,
                remoteDataSource,
                cuisineRepository
            )
        return GetRecipesByRegion(recipeRepository)
    }
}