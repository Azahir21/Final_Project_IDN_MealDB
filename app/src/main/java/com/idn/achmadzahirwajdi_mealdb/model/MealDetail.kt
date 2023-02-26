package com.idn.achmadzahirwajdi_mealdb.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MealDetail(

	@field:SerializedName("meals")
	val meals: List<MealsDetailItem?>? = null
	//val meals: MealsDetailItem
) : Parcelable