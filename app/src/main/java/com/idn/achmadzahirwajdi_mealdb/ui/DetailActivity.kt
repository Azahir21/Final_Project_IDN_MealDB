package com.idn.achmadzahirwajdi_mealdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.data.database.MealEntity
import com.idn.achmadzahirwajdi_mealdb.data.network.handler.NetworkResult
import com.idn.achmadzahirwajdi_mealdb.databinding.ActivityDetailBinding
import com.idn.achmadzahirwajdi_mealdb.model.Meal
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import com.idn.achmadzahirwajdi_mealdb.model.MealsItem
import com.idn.achmadzahirwajdi_mealdb.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var mealDetail: MealDetail

    companion object {
        const val EXTRA_MEAL = "extra_meal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Detail Meal"
            setDisplayHomeAsUpEnabled(true)
        }

        val meal = intent.getParcelableExtra<MealsItem>(EXTRA_MEAL)

        detailViewModel.fetchMealDetail(meal?.idMeal!!)

        detailViewModel.mealDetail.observe(this) {res ->
            when(res){
                is NetworkResult.Loading -> handleUi(
                    layoutWrapper = false,
                    progressBar = true,
                    errorTv = false,
                    errorImg = false
                )
                is NetworkResult.Error -> {
                    handleUi(
                        layoutWrapper = false,
                        progressBar = false,
                        errorTv = true,
                        errorImg = true
                    )
                    Toast.makeText(this, res.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    val selectedMeal = res.data?.meals!![0]
                    binding.mealDetail = selectedMeal
                    mealDetail = res.data!!
                    handleUi(
                        layoutWrapper = true,
                        progressBar = false,
                        errorTv = false,
                        errorImg = false
                    )
                }
            }

        }
        isFavoriteMeal(meal)
    }

    private fun isFavoriteMeal(mealSelected: MealsItem){
        detailViewModel.favoriteMealList.observe(this) { res ->
            val meal = res.find { favorite ->
                favorite.meal.meals!![0]?.idMeal == mealSelected.idMeal
            }
            if(meal != null){
                binding.addFavoriteButton.apply {
                    setOnClickListener {
                        deleteFavoriteGame(meal.id)
                    }
                    setImageResource(R.drawable.ic_favorite_full)
                }
            } else {
                binding.addFavoriteButton.apply {
                    setOnClickListener {
                        insertFavoriteGame()
                    }
                    setImageResource(R.drawable.ic_favorite)
                }
            }
        }
    }

    private fun deleteFavoriteGame(gameEntityId: Int) {
        val mealEntity = MealEntity(gameEntityId,mealDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteGame() {
        val mealEntity = MealEntity(meal = mealDetail)
        detailViewModel.insertFavoriteGame(mealEntity)
        Toast.makeText(this, "Successfully added to favorite", Toast.LENGTH_SHORT).show()
    }


    private fun handleUi(
        layoutWrapper: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
        errorImg: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = layoutWrapper
            progressbar.isVisible = progressBar
            errorIcon.isVisible = errorImg
            errorText.isVisible = errorTv
        }
    }
}