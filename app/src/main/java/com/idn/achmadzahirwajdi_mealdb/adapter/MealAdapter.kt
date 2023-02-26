package com.idn.achmadzahirwajdi_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.databinding.ItemRowsMealBinding
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import com.idn.achmadzahirwajdi_mealdb.model.MealsItem
import com.idn.achmadzahirwajdi_mealdb.ui.DetailActivity

class MealAdapter: RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private var dataMeal: List<MealsItem> = listOf()

    inner class MealViewHolder(private val binding: ItemRowsMealBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MealsItem) {
            binding.apply {
                tvJudul.text = data.strMeal

                Glide.with(imgMeal)
                    .load(data.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgMeal)

                binding.cardMeal.setOnClickListener(){
                    val intent =Intent(cardMeal.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MEAL, data)
                    cardMeal.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.MealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = ItemRowsMealBinding.inflate(layoutInflater, parent, false)
        return MealViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: MealAdapter.MealViewHolder, position: Int) {
        return holder.bind(dataMeal[position])
    }

    override fun getItemCount(): Int {
        return dataMeal.size
    }

    fun setData(data: List<MealsItem> ){
        dataMeal = data
        notifyDataSetChanged()
    }
}