package com.idn.achmadzahirwajdi_mealdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.adapter.FavoriteAdapter
import com.idn.achmadzahirwajdi_mealdb.adapter.MealAdapter
import com.idn.achmadzahirwajdi_mealdb.databinding.ActivityFavoriteBinding
import com.idn.achmadzahirwajdi_mealdb.model.MealsItem
import com.idn.achmadzahirwajdi_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> ()
    private val favoriteMealAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meal"
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteMealList.observe(this) { result ->
            if(result.isEmpty()){
                binding.apply {
                    rvMealFavorite.isVisible = false
                    emptyTv.isVisible = true
                    emptyIcon.isVisible = true
                }
            } else {
                binding.rvMealFavorite.apply {
                    layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    setHasFixedSize(true)
                    favoriteMealAdapter.setData(result)
                    adapter = favoriteMealAdapter
                }
            }
        }
    }
}