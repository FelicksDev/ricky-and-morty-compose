package com.felicks.testcomposeproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.navigation.AppNavigation
import com.felicks.testcomposeproject.presentation.screens.MainScreen

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
//            MainScreen()
            AppNavigation()
        }
//        viewModel.getCharacters()
    }
}

