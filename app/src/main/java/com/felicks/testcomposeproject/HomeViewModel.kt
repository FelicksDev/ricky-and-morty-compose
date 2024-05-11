package com.felicks.testcomposeproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Location
import com.felicks.testcomposeproject.domain.model.Origin
import com.felicks.testcomposeproject.services.ApiService
import com.felicks.testcomposeproject.services.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class HomeViewModel : ViewModel() {
    //retrofit
    private val retrofitInstance = RetrofitInstance()
    private val retrofitService: ApiService = retrofitInstance.service

    ///
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    //
    private val _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean> = _showSnackbar
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    ///
    private val _character = MutableStateFlow<Character>(Character(
        "",
        emptyList(),
        "",
        0,
        "",
        Location("", ""),
        "",
        Origin("", ""),
        "",
        "",
        "",
        ""
    ))
    val character: StateFlow<Character> = _character

    init {
        getCharacters()
    }

    fun getCharacters(): List<Character> {
        //no esta del tdo bien ya que debe extraerse de la capa de data yno del viewmodel

        //Realizar validacions de errores
        var charLista: List<Character> = emptyList();

        /// TODO El error puede serp oque se ese ejecute la corutina en todo el tryuacht y solo debria ser para el request
        viewModelScope.launch {
            _isLoading.value = true
            // Realizar operaciones asíncronas, como hacer una solicitud de red
            try {
                // Almacenar en estado LiveData
                val result = retrofitService.getCharacters()
                _isLoading.value = false
                _characters.value = result.results
                // Actualizar los datos en el LiveData u otro medio de comunicación con la Vista
            } catch (e: Exception) {

                when (e) {
                    is UnknownHostException -> {
                        // Manejar el error "Unable to resolve host"
                        _showSnackbar.value = true
//                         _snackbarMessage.value = "No se puede conectar al servidor. Verifique su conexión a Internet."
                        _isLoading.value = false
                    }

                    else -> {
                        // Manejar otras excepciones
                        _showSnackbar.value = true
//                         _snackbarMessage.value = "Ocurrió un error. Intente de nuevo."
                        _isLoading.value = false
                        Log.d("HomeViewModel", "Error: ${e.message}")
                    }
                }
            }
//            _isLoading.value = false
        }
        return charLista
    }
    // TODO get Char by ID

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            try {
                val result = retrofitService.getCharacterById(id)
                _character.value = result
                Log.d("HomeViewModel", "Character Individual es: ${result.name}")
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.message}")
            }
        }
    }


}