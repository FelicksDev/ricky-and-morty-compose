package com.felicks.testcomposeproject.services

import com.felicks.testcomposeproject.domain.model.Result
import com.felicks.testcomposeproject.domain.model.RickyAndMortyResponse
import retrofit2.http.GET

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(): RickyAndMortyResponse
}