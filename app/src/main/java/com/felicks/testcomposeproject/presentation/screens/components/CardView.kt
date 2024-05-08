package com.felicks.testcomposeproject.presentation.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Location
import com.felicks.testcomposeproject.domain.model.Origin

val rickSanchez: Character = Character(
    id = 1,
    name = "Abadango Cluster Princess",
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

@Composable
fun CardView(personaje: Character, isExpanded: Boolean = false) {
    //TODO Agregar logica para toogle usando un estado
    var expanded by remember { mutableStateOf(isExpanded) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize()
            .border(BorderStroke(1.dp, Color.Black))
            .clickable { expanded = !expanded }

    ) {
        Column {
            Row(
                modifier = Modifier.padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardImage(personaje = personaje)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    CardTitle(personaje = personaje)
                    CardDescription(personaje = personaje)
                }
                //TODO agregaer logica de toogle

                ShowMoreButton(Modifier.padding(end=10.dp), expanded)
            }
        }
        if (expanded) CardDetail(personaje = personaje)


    }
}

@Composable
fun CardDetail(personaje: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Origin: ${personaje.origin.name}")
        Text(text = "Location: ${personaje.location.name}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { /* Acción de botón */ }) {
                Text(text = "More")
            }
        }
    }
}

@Composable
fun ShowMoreButton(modifier: Modifier = Modifier, expanded: Boolean) {
//    Button(
//        // Suponiendo que quieres un botón "Ver más" interactivo
//        onClick = { /* Maneja el evento de clic */ },
//        modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp)
//            .background(Color.Red),
//    ) {
//        Text(text = "+", style = MaterialTheme.typography.titleLarge, color = Color.Black)
//    }
// Agregar logica para toogle usando un estado


        Icon(
            if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
            modifier = modifier
        )


}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    CardView(personaje = rickSanchez)
}

@Composable
fun CardImage(personaje: Character) {
    AsyncImage(
        model = personaje.image,
        contentDescription = personaje.name,
        modifier = Modifier
            .size(85.dp, 85.dp)
            .clip(CircleShape)
            .wrapContentHeight()
    )
//    Image(
//        painter = painterResource(id = R.drawable.ricksanchez),
//        contentDescription = "Rick Sanchez",
//        modifier = Modifier
//            .size(100.dp, 100.dp)
//            .clip(CircleShape)
//    )
}

@Composable
fun CardTitle(personaje: Character) {
    Text(
        text = personaje.name,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2,
        overflow = TextOverflow.Clip,

        )
}

@Composable
fun CardDescription(personaje: Character) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                text = personaje.status,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = personaje.species,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}