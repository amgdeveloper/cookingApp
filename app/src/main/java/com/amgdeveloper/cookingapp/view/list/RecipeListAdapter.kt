package com.amgdeveloper.cookingapp.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amgdeveloper.cookingapp.common.loadImage
import com.amgdeveloper.cookingapp.databinding.RecipeListItemBinding
import com.amgdeveloper.cookingapp.model.Recipe
import com.bumptech.glide.Glide

/**
 * Created by amgdeveloper on 19/11/2020
 */
class RecipeListAdapter(var recipes: List<Recipe>,
                        private val recipeListener: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipeListItemTitleTv.text = recipe.title
            binding.recipeListItemIv.loadImage(recipe.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeListItemBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes.get(position))
        holder.itemView.setOnClickListener { recipeListener(recipes[position]) }
    }
}