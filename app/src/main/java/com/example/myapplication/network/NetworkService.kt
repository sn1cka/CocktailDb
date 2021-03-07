package com.example.myapplication.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private val interceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/"

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    private val mRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val mainApi: MainApi = mRetrofit.create(
        MainApi::class.java)
}