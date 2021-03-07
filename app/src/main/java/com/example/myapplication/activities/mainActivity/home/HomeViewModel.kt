package com.example.myapplication.activities.mainActivity.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.BuildConfig
import com.example.myapplication.models.Cocktail
import com.example.myapplication.models.CocktailResponse
import com.example.myapplication.network.NetworkService
import com.example.myapplication.utils.CacheHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val isVMRefreshing = MutableLiveData<Boolean>(false)
    val cocktail = MutableLiveData<Cocktail>()
    val successResponse = MutableLiveData<Boolean>(true)
    val ingridients = MutableLiveData<ArrayList<String>>()

    private val mainApi = NetworkService.mainApi


    fun refreshRandomCocktail() {
        isVMRefreshing.value = true
        ingridients.value = arrayListOf()
        mainApi.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {
            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                if (BuildConfig.DEBUG) {
                    t.printStackTrace()
                }
                isVMRefreshing.value = false
                successResponse.value = false
            }

            override fun onResponse(
                call: Call<CocktailResponse>,
                response: Response<CocktailResponse>
            ) {
                cocktail.value = response.body()!!.drinks!![0]
                isVMRefreshing.value = false
                successResponse.value = true
                CacheHelper.addToHistory(getApplication(), cocktail.value!!)
            }

        })
    }

    fun getDetailedInfo() {
        if (cocktail.value != null && !isVMRefreshing.value!!)
            mainApi.getIngridientsById(cocktail.value!!.idDrink!!)
                .enqueue(object : Callback<CocktailResponse> {
                    override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace()
                        }
                    }

                    override fun onResponse(
                        call: Call<CocktailResponse>,
                        response: Response<CocktailResponse>
                    ) {
                        val json = Gson().toJson(response.body()!!.drinks!!.first())
                        var map = HashMap<String, String>()
                        map = Gson().fromJson(json, map.javaClass)
                        val mList = arrayListOf<String>()
                        for (item in map.filter { it.key.contains("strIngredient") }) {
                            mList.add(item.value)
                        }
                        ingridients.value = mList
                    }
                })

    }
}
