package com.felicks.testcomposeproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.services.ApiService
import com.felicks.testcomposeproject.services.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //retrofit
    private val retrofitInstance = RetrofitInstance()
    private val retrofitService: ApiService = retrofitInstance.service

    ///
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    init {
        getCharacters()
    }
    fun getCharacters(): List<Character> {
        //no esta del tdo bien ya que debe extraerse de la capa de data yno del viewmodel

        //Realizar validacions de errores
        var charLista: List<Character> = emptyList();
        viewModelScope.launch {
            // Realizar operaciones asíncronas, como hacer una solicitud de red
            try {
                // Almacenar en estado LiveData
                val result = retrofitService.getCharacters()
                _characters.value = result.results
                // Actualizar los datos en el LiveData u otro medio de comunicación con la Vista
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.message}")
            }

        }
        return charLista
    }

}