package com.felicks.testcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.felicks.testcomposeproject.presentation.screens.DetailScreen
import com.felicks.testcomposeproject.presentation.screens.MainScreen

@Composable
fun AppNavigation( ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route){
        //Aqui se define la navegación por Prog. imperativa. En XML era declarativa
        composable( route = AppScreens.MainScreen.route){
            MainScreen(navController)
        }
        composable( route = AppScreens.DetailScreen.route){
            DetailScreen(navController)
        }
    }
}