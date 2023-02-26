package com.idn.achmadzahirwajdi_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import com.idn.achmadzahirwajdi_mealdb.utils.Constant.MEAL_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = MEAL_TABLE_NAME)
@Parcelize
data class MealEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: MealDetail
): Parcelable