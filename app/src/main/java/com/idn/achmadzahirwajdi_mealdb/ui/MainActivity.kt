package com.idn.achmadzahirwajdi_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.achmadzahirwajdi_mealdb.R
import com.idn.achmadzahirwajdi_mealdb.adapter.MealAdapter
import com.idn.achmadzahirwajdi_mealdb.data.network.handler.NetworkResult
import com.idn.achmadzahirwajdi_mealdb.databinding.ActivityMainBinding
import com.idn.achmadzahirwajdi_mealdb.model.MealsItem
import com.idn.achmadzahirwajdi_mealdb.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Seafood Meal"

        mainViewModel.mealList.observe(this@MainActivity) { res ->
            when(res){
                is NetworkResult.Loading -> {
                    handleUi(
                        recyclerView = false,
                        progressbar = true,
                        errorTv = false,
                    )
                }
                is NetworkResult.Error -> {
                    binding.errorText.text = res.errorMessage
                    handleUi(
                        recyclerView = false,
                        progressbar = false,
                        errorTv = true,
                    )
                    Toast.makeText(this,res.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success ->{
                    val mealAdapter = MealAdapter()
                    Log.d("SUCCESS", "Success retrieved data")
                    mealAdapter.setData(res.data?.meals as List<MealsItem>)

                    binding.rvMeal.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        setHasFixedSize(true)
                        adapter = mealAdapter
                    }

                    handleUi(
                        recyclerView = true,
                        progressbar = false,
                        errorTv = false,
                    )
                }
            }
        }
    }

    private fun handleUi(
        recyclerView: Boolean,
        progressbar: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            rvMeal.isVisible = recyclerView
            progressBar.isVisible = progressbar
            errorText.isVisible = errorTv
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}