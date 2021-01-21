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
import com.amgdeveloper.cookingapp.ui.common.PermissionRequester
import com.amgdeveloper.cookingapp.common.app
import com.amgdeveloper.cookingapp.common.getViewModel
import com.amgdeveloper.cookingapp.common.startActivity
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.ui.detail.RecipeDetailsActivity
import com.amgdeveloper.cookingapp.ui.main.ListViewModel.UiModel.*

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {

    private lateinit var component: RecipeListFragmentComponent
    private val viewModel: ListViewModel by lazy { getViewModel { component.listViewModel } }
    private lateinit var adapter: RecipeListAdapter
    private lateinit var progressDialog: ProgressBar
    private lateinit var coarsePermissionRequester : PermissionRequester
    companion object {
        val TAG: String = RecipeListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
          coarsePermissionRequester = PermissionRequester(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        component = requireContext().app.component.plus(RecipeListFragmentModule())

        val recyclerView = binding.recipeListFragmentRv
        progressDialog = binding.recipeListFragmentPb


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
}