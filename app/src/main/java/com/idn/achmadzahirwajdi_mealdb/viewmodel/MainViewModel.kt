package com.idn.achmadzahirwajdi_mealdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idn.achmadzahirwajdi_mealdb.data.RemoteDataSource
import com.idn.achmadzahirwajdi_mealdb.data.Repository
import com.idn.achmadzahirwajdi_mealdb.data.network.Service
import com.idn.achmadzahirwajdi_mealdb.data.network.handler.NetworkResult
import com.idn.achmadzahirwajdi_mealdb.model.Meal
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val remoteService = Service.MealService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _mealList:MutableLiveData<NetworkResult<Meal>> = MutableLiveData()
    val mealList:LiveData<NetworkResult<Meal>> = _mealList

    init {
        fetchMealList()
    }

    private fun fetchMealList(){
        viewModelScope.launch {
            repository.remote?.getMeal()?.collect() { res ->
                _mealList.value = res
            }
        }
    }
}
