package com.idn.achmadzahirwajdi_mealdb.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Meal(

	@field:SerializedName("meals")
	val meals: List<MealsItem>? = null
) : Parcelable
