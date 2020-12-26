package com.amgdeveloper.cookingapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amgdeveloper.cookingapp.R
import com.amgdeveloper.cookingapp.common.fragmentExists
import com.amgdeveloper.cookingapp.databinding.ActivityMainBinding
import com.amgdeveloper.cookingapp.view.list.RecipeListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root

        setContentView(rootView)
        displayListFragment()
    }

    private fun displayListFragment() {
        if (!supportFragmentManager.fragmentExists(RecipeListFragment.TAG)) {
            val fragment = RecipeListFragment()
            supportFragmentManager.beginTransaction().replace(
                    R.id.mainActivityFl, fragment, RecipeListFragment.TAG).commit()
        }
    }
}