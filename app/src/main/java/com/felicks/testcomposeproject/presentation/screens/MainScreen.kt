package com.felicks.testcomposeproject.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.felicks.testcomposeproject.HomeViewModel
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Location
import com.felicks.testcomposeproject.domain.model.Origin
import com.felicks.testcomposeproject.presentation.screens.components.CardView

val rickSanchez: Character = Character(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = Origin(
        name = "Earth (C-137)",
        url = "https://rickandmortyapi.com/api/location/1"
    ),
    location = Location(
        name = "Citadel of Ricks",
        url = "https://rickandmortyapi.com/api/location/3"
    ),
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    //TODO Agregar splash screen
    // Dentro de MainScreen
    var viewModel = HomeViewModel()
    val characters by viewModel.characters.observeAsState(emptyList())
//    characters?.forEach {
//        characters ->
////        println("Character: ${characters.name}")
//        Log.d("MainScreen", "Character: ${characters.name}")
//    }
//    Log.d("MainScreen", "Character: ${characters}")
    // Mencionar en documentacion all esto
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    Text("Small Top App Bar")
                }
            )
        },
    ) {
        innerPadding ->
        BodyContent(characters, navController, innerPadding )
    }

}

@Composable
fun BodyContent(characters:List<Character>, navController: NavController, paddingValues: PaddingValues) {
    Box(modifier = Modifier. padding(paddingValues)) {
        // TODO Mejorar diseño y hacer en funcion composable

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .background(Color.Red),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Mi Favorite App",
                    style = typography.titleLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "This is a simple app to show how to use Jetpack Compose",
                    style = typography.bodyMedium
                )
            }
            LazyColumn {
                items(characters.size) {
                    CardView(
                        //TODO: Se debe mandar el id para la siguiente screen
                        personaje = characters[it], navController = navController
                    )
                }
            }
        }
    }
}
