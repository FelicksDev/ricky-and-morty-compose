package com.felicks.testcomposeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.felicks.testcomposeproject.HomeViewModel
import com.felicks.testcomposeproject.presentation.screens.DetailScreen
import com.felicks.testcomposeproject.presentation.screens.MainScreen

@Composable
fun AppNavigation( viewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route){
        //Aqui se define la navegación por Prog. imperativa. En XML era declarativa
        composable( route = AppScreens.MainScreen.route){
            MainScreen(navController,viewModel)
        }
        composable( route = AppScreens.DetailScreen.route+"/{characterId}",
                    arguments = listOf(navArgument("characterId"){
                        type = NavType.IntType
                    })
            ){
            DetailScreen(navController, it.arguments?.getInt("characterId"),viewModel)
        }
    }
}