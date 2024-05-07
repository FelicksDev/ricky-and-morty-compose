package com.felicks.testcomposeproject.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felicks.testcomposeproject.R
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.domain.model.Location
import com.felicks.testcomposeproject.domain.model.Origin

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

@Composable
fun CardView(personaje: Character) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row (modifier = Modifier.padding(horizontal = 10.dp)) {
//            AsyncImage(
//                model = personaje.image,
//                contentDescription = personaje.name,
//                modifier = Modifier
//                    .size(160.dp)
//                    .clip(CircleShape)
//            )
            CardImage(personaje =personaje)
            Column (modifier = Modifier.padding(15.dp)){
                CardTitle(personaje =personaje)
                    CardDescription(personaje =personaje)


            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    CardView(personaje = rickSanchez)
}

@Composable
fun CardImage(personaje: Character) {
    Image(
        painter = painterResource(id = R.drawable.ricksanchez),
        contentDescription = "Rick Sanchez",
        modifier = Modifier
            .size(100.dp, 100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun CardTitle(personaje: Character) {
    Text(
        text = personaje.name,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2,
        overflow = TextOverflow.Clip
    )
}

@Composable
fun CardDescription(personaje: Character) {
   Box(modifier = Modifier.fillMaxWidth()){
       Row (modifier = Modifier.padding(vertical = 10.dp)){
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