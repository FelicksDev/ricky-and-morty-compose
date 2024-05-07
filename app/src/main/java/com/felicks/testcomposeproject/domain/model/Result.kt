package com.felicks.testcomposeproject.domain.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("character")
    //El serialized name es el nombre que tiene en la api
//    De otra manera no tomará el nombre de la clase por defecto
    //En realidad es el character
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)