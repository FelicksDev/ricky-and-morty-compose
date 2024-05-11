package com.felicks.testcomposeproject.services

import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Result
import com.felicks.testcomposeproject.domain.model.RickyAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(): RickyAndMortyResponse
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id")  id: Int): Character
}