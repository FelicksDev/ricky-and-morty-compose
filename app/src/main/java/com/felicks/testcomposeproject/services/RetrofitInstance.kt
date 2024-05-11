package com.felicks.testcomposeproject.services

import retrofit2.Retrofit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.converter.gson.GsonConverterFactory




class RetrofitInstance {
    private val BASE_URL = "https://rickandmortyapi.com/api/"
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service  = retrofitBuilder.create(ApiService::class.java)



}
