package com.felicks.testcomposeproject.presentation.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.felicks.testcomposeproject.HomeViewModel
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Origin

val rickCharacter = rickSanchez

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    characterId: Int? = 0,
    homeViewModel: HomeViewModel
) {
    val myCharacterState by homeViewModel.character.collectAsState()

//    val myCharacterState by homeViewModel.getCharacterDetail(characterId).collectAsState()

    LaunchedEffect(characterId) {
        homeViewModel.getCharacterDetail(characterId!!)
        Log.d("DetailScreen", "El id CharacterId: $characterId")
    }

    val myCharacter = myCharacterState
    Log.d("DetailScreen", "Character: ${myCharacter.name}")

    //TODO Agregar splash screen
    // Dentro de MainScreen
    // var viewModel = HomeViewModel()
    // val characters by viewModel.characters.observeAsState(emptyList())
    // characters?.forEach {
    //     characters ->
    //     println("Character: ${characters.name}")
    //     Log.d("MainScreen", "Character: ${characters.name}")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Rick And Morty App")
                },
                navigationIcon = {
                    //TODO en popbackstack los datos en ael main screen se pierden se deb agregar persitencai de datos o hacerlo una lista mutable o inmutable xd

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }

            )
        },
    ) { paddingValues ->
        BodyScreen(paddingValues, myCharacter)
    }


}

@Composable
fun BodyScreen(padding: PaddingValues?, character: Character) {
    Column(
        modifier = Modifier
            .padding(padding!!),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderContent(character)
        BodyContent(character)

    }
}

@Composable
fun BodyContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
           ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatusContent(character)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            Text(text = "Location: ${character.location.name}", modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium)
            Text(text = "Origin: ${character.origin.name}", modifier = Modifier.weight(1f), style= MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun StatusContent(character: Character) {
    val statusColor = when (character.status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Yellow
    }
    Row {
        Text(text = "Status: ")
        Canvas(
            modifier = Modifier.padding(10.dp),
            onDraw = {

                drawCircle(
                    color = statusColor,
                    radius = 15f,
                    center = this.center
                )
                drawCircle(
                    color = Color.Black,
                    style = Stroke(4f),
                    center = this.center,
                    radius = 17f
                )
            }
        )
        Text(text = " ${character.status}",
            style= MaterialTheme.typography.bodyLarge,)
    }

}
@Preview(showBackground = true)
@Composable
fun StatusContentPreview () {
    StatusContent(character = rickCharacter)
}

@Composable
fun HeaderContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Subject ${getIdentifier(character.id, character.origin)}",
            style = MaterialTheme.typography.titleMedium)
        //TODO Geenrar codigo especial basao en elorigin con enum
        Surface ( shape = MaterialTheme.shapes.medium,
             ){
            AsyncImage(
                model = character.image, contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .size(180.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(20.dp, MaterialTheme.shapes.medium)
            )
        }


        Text(text = character.name,
            style = MaterialTheme.typography.titleLarge)
        Text(text = character.species,
            style = MaterialTheme.typography.bodyMedium)


    }

}

fun getIdentifier(id: Int, origin: Origin): String {
    val identifier: String = when (origin.name) {
        "Abadango" -> "AB-0$id"
        "Earth (C-137)" -> "C137-0$id"
        "Earth (Replacement Dimension)" -> "ERD-0$id"
        else -> "???-0$id"
    }
    return identifier
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    BodyScreen(character = rickCharacter, padding = PaddingValues(0.dp))
}