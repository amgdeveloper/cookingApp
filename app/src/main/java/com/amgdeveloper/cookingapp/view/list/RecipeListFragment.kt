package com.amgdeveloper.cookingapp.view.list

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amgdeveloper.cookingapp.CoarseLocationPermissionRequester
import com.amgdeveloper.cookingapp.common.getViewModel
import com.amgdeveloper.cookingapp.common.startActivity
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.network.RecipeRepository
import com.amgdeveloper.cookingapp.view.details.RecipeDetailsActivity
import com.amgdeveloper.cookingapp.view.list.ListViewModel.UiModel.*

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {


    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(requireActivity().application) }
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
        viewModel = getViewModel { ListViewModel(recipeRepository) }
        adapter = RecipeListAdapter(emptyList(), viewModel::onRecipeClicked)
        recyclerView.adapter = adapter
        viewModel.model.observe(viewLifecycleOwner, Observer(this::updateUi))

        viewModel.navigation.observe(viewLifecycleOwner,Observer {event ->
            event.getContentIfNotHandled()?.let {
                context?.startActivity<RecipeDetailsActivity> {
                    putExtra(RecipeDetailsActivity.EXTRA_RECIPE, it)
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
}