package com.idn.achmadzahirwajdi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.idn.achmadzahirwajdi_mealdb.data.LocalDataSource
import com.idn.achmadzahirwajdi_mealdb.data.RemoteDataSource
import com.idn.achmadzahirwajdi_mealdb.data.Repository
import com.idn.achmadzahirwajdi_mealdb.data.database.MealDatabase
import com.idn.achmadzahirwajdi_mealdb.data.database.MealEntity
import com.idn.achmadzahirwajdi_mealdb.data.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailViewModel (application: Application): AndroidViewModel(application) {
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote, local)

    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}