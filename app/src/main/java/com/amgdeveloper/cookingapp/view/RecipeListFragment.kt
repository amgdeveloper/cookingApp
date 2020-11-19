package com.amgdeveloper.cookingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amgdeveloper.cookingapp.BuildConfig
import com.amgdeveloper.cookingapp.databinding.FragmentRecipeListBinding
import com.amgdeveloper.cookingapp.network.RecipeClient
import kotlin.concurrent.thread

/**
 * Created by amgdeveloper on 18/11/2020
 */
class RecipeListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thread{
            val map = mutableMapOf<String,String>()
            map["apiKey"] = BuildConfig.API_KEY
            map["cuisine"] = "italian"    //search italian recipes by default
            val call = RecipeClient.service.getRandomRecipes(map)
            val body = call.execute().body()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipeListBinding.inflate(layoutInflater,container,false)
        val rootView = binding.root

        return rootView
    }

}