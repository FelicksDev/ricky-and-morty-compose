package com.felicks.testcomposeproject.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.felicks.testcomposeproject.HomeViewModel
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Location
import com.felicks.testcomposeproject.domain.model.Origin
import com.felicks.testcomposeproject.presentation.screens.components.CardView
import kotlinx.coroutines.launch

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
    navController: NavController,
    viewModel: HomeViewModel // Inyectar ViewModel
) {
    //TODO Agregar splash screen
    // Dentro de MainScreen
    val characters by viewModel.characters.observeAsState(emptyList())
    val showSnackbar by viewModel.showSnackbar.observeAsState(false) // Recolectar estado de error
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope();
    //TODO: Lanzar  snackbar para solicitar otra peticion si no se obtiene nada
    // Mencionar en documentacion all esto
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier= Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior,
                title = {
                    Text("Rick And Morty App")
                },
                actions = {
                    IconButton(onClick = { scope.launch { var result = snackbarHostState.showSnackbar(
                        message = "Ocurrió un error de red",
                        duration = SnackbarDuration.Long,
                        actionLabel = "Reintentar",
                        )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                //Realizar la acción de reintentar
                                //TODO ESTABLECER TIME OUT POR DOS SEGUNDOS
                                viewModel.getCharacters()
                            }
                            SnackbarResult.Dismissed -> {
                                // No hacer nada
                            }
                        }
                    } }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
//    val snackVisual : SnackbarVisuals =
        // TODO: Agregar un snackbar para mostrar un mensaje de error
        // Mostrar Snackbar si no se obtiene respuesta del server
//    corregir errores proque no funciona correctament
        if (showSnackbar) {
            LaunchedEffect(Unit) { // Efecto de lanzamiento para mostrar Snackbar solo una vez
                var result = snackbarHostState.showSnackbar(
                    message = "Ocurrió un error de red. Intente de nuevo.",
                    duration = SnackbarDuration.Long,
                    actionLabel = "Reintentar"
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        //Realizar la acción de reintentar
                        //TODO ESTABLECER TIME OUT POR DOS SEGUNDOS
                        viewModel.getCharacters()
                    }
                    SnackbarResult.Dismissed -> {
                        // No hacer nada
                    }
                }
            }
        }
        BodyContent(
            characters,
            navController,
            innerPadding
        )
    }

}

@Composable
fun BodyContent(
    characters: List<Character>,
    navController: NavController,
    paddingValues: PaddingValues
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        // TODO Mejorar diseño y hacer en funcion composable
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 35.dp, start=  35.dp, end= 35.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Rick And Morty Character (Minimal) guide",
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Breverly list of characters from the Rick and Morty series",
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
