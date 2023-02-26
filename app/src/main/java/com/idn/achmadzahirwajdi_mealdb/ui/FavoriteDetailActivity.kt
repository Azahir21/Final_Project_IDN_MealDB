package com.idn.achmadzahirwajdi_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.data.database.MealEntity
import com.idn.achmadzahirwajdi_mealdb.databinding.ActivityFavoriteDetailBinding
import com.idn.achmadzahirwajdi_mealdb.viewmodel.FavoriteDetailViewModel

class FavoriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDetailBinding
    private val favoriteDetailViewModel by viewModels<FavoriteDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Detail Meal"
            setDisplayHomeAsUpEnabled(true)
        }

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)
        binding.mealDetail = favoriteMeal?.meal?.meals!![0]

        binding.removeFavoriteButton.setOnClickListener{
            deleteFavoriteMeal(favoriteMeal)
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun deleteFavoriteMeal(mealEntity: MealEntity) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }
}