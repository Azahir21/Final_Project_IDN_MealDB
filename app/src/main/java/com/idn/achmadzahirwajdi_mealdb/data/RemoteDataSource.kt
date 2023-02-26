package com.idn.achmadzahirwajdi_mealdb.data

import android.util.Log
import com.idn.achmadzahirwajdi_mealdb.data.network.api.MealApi
import com.idn.achmadzahirwajdi_mealdb.data.network.handler.NetworkResult
import com.idn.achmadzahirwajdi_mealdb.model.Meal
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val mealApi: MealApi) {
    suspend fun  getMeal(): Flow<NetworkResult<Meal>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val meal = mealApi.getMeal()

            if(meal.isSuccessful) {
                val responseBody = meal.body()

                if (responseBody?.meals?.isEmpty() == true){
                    emit(NetworkResult.Error("Meal List Not Found"))
                }else {
                    emit(NetworkResult.Success(responseBody!!))
                }

            } else {
                Log.d("apiServiceError", "statusCode:${meal.code()}, message:${meal.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }

        } catch (e:Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealDetailById(mealId: String): Flow<NetworkResult<MealDetail>> = flow{
        try {
            emit(NetworkResult.Loading(true))
            val mealDetail = mealApi.getMealDetailById(mealId)
            if (mealDetail.isSuccessful){
                val responseBody = mealDetail.body()

                if (responseBody != null){
                    emit(NetworkResult.Success(responseBody))
                } else {
                    emit(NetworkResult.Error("Can't fetch detail Meal."))
                }
            } else {
                Log.d("apiServiceError", "statusCode:${mealDetail.code()}, message:${mealDetail.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}