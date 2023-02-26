package com.idn.achmadzahirwajdi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.idn.achmadzahirwajdi_mealdb.data.LocalDataSource
import com.idn.achmadzahirwajdi_mealdb.data.RemoteDataSource
import com.idn.achmadzahirwajdi_mealdb.data.Repository
import com.idn.achmadzahirwajdi_mealdb.data.database.MealDatabase
import com.idn.achmadzahirwajdi_mealdb.data.database.MealEntity
import com.idn.achmadzahirwajdi_mealdb.data.network.Service
import com.idn.achmadzahirwajdi_mealdb.data.network.handler.NetworkResult
import com.idn.achmadzahirwajdi_mealdb.model.MealDetail
import com.idn.achmadzahirwajdi_mealdb.model.MealsDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel (application: Application): AndroidViewModel(application){
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote, local)

    private var _mealDetail: MutableLiveData<NetworkResult<MealDetail>> = MutableLiveData()
    val mealDetail: LiveData<NetworkResult<MealDetail>> = _mealDetail

    fun fetchMealDetail(mealId:String) {
        viewModelScope.launch {
            repository.remote!!.getMealDetailById(mealId). collect{ res ->
                _mealDetail.value = res
            }
        }
    }

    val favoriteMealList:LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
    fun insertFavoriteGame(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}