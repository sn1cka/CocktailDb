package com.example.myapplication.network

import com.example.myapplication.models.CocktailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("1/random.php")
    fun getRandomCocktail():Call<CocktailResponse>
    @GET("1/lookup.php")
    fun getIngridientsById(@Query("i") id:String):Call<CocktailResponse>

}