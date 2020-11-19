package com.amgdeveloper.cookingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amgdeveloper.cookingapp.R
import com.amgdeveloper.cookingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root

        setContentView(rootView)
        displayListFragment()
    }

    private fun displayListFragment(){
        val fragment = RecipeListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mainActivityFl,fragment).commit()
    }
}