package com.idn.achmadzahirwajdi_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.data.database.MealEntity
import com.idn.achmadzahirwajdi_mealdb.databinding.ItemRowsMealFavoriteBinding
import com.idn.achmadzahirwajdi_mealdb.model.MealsItem
import com.idn.achmadzahirwajdi_mealdb.ui.DetailActivity
import com.idn.achmadzahirwajdi_mealdb.ui.FavoriteDetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var dataMeal: List<MealEntity> = listOf()

    inner class FavoriteViewHolder(private val binding: ItemRowsMealFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MealEntity) {
            binding.apply {
                tvJudulFavorite.text = data.meal.meals!![0]?.strMeal

                Glide.with(imgMealFavorite)
                    .load(data.meal.meals!![0]?.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgMealFavorite)

                binding.cardMealFavorite.setOnClickListener(){
                    val intent =Intent(cardMealFavorite.context, FavoriteDetailActivity::class.java)
                    intent.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_MEAL, data)
                    cardMealFavorite.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = ItemRowsMealFavoriteBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        return holder.bind(dataMeal[position])
    }

    override fun getItemCount(): Int {
        return dataMeal.size
    }

    fun setData(data: List<MealEntity> ){
        dataMeal = data
        notifyDataSetChanged()
    }

}