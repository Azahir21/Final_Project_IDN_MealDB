package com.idn.achmadzahirwajdi_mealdb.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal: MealDetail): String{
        return gson.toJson(meal)
    }

    @TypeConverter
    fun mealStringToData(string: String): MealDetail{
        val listType = object : TypeToken<MealDetail>() {}.type
        return  gson.fromJson(string, listType)
    }
}