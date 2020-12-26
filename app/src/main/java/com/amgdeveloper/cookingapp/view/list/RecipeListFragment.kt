package com.amgdeveloper.cookingapp.view.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.amgdeveloper.cookingapp.network.RecipeRepository
import com.amgdeveloper.cookingapp.view.details.RecipeDetailsActivity

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment(), ListPresenter.View {


    private val adapter = RecipeListAdapter(emptyList()) { presenter.onRecipeClicked(it) }
    private val recipeRepository: RecipeRepository by lazy { RecipeRepository(activity as AppCompatActivity) }
    private val presenter : ListPresenter by lazy {ListPresenter(recipeRepository)}
    private lateinit var progressDialog : ProgressBar

    companion object {
        val TAG: String = RecipeListFragment::class.java.simpleName
    }


    override fun onAttach(context: Context) {
        presenter.onCreate(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        val recyclerView = binding.recipeListFragmentRv
        progressDialog = binding.recipeListFragmentPb
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progressDialog.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressDialog.visibility = View.GONE
    }

    override fun updateData(list: List<Recipe>) {
        adapter.recipes = list
        adapter.notifyDataSetChanged()
    }

    override fun navigateTo(recipe: Recipe) {
        val intent = Intent(context, RecipeDetailsActivity::class.java)
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipe)
        startActivity(intent)
    }
}