package com.idn.achmadzahirwajdi_mealdb.data.network.api

import com.idn.achmadzahirwajdi_mealdb.model.Meal
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMeal() : Response<Meal>

    @GET("lookup.php")
    suspend fun getMealDetailById(
        @Query("i") i:String
    ):Response<MealDetail>
}