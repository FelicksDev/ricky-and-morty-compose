package com.felicks.testcomposeproject.presentation.screens.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.felicks.testcomposeproject.domain.model.Character
import com.felicks.testcomposeproject.navigation.AppScreens

@Composable
fun CardView(personaje: Character, isExpanded: Boolean = false, navController: NavController) {
    var expanded by remember { mutableStateOf(isExpanded) }
    var maxLines = if (expanded) 2 else 1
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
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

                    CardTitle(personaje = personaje, maxLines)
                    CardDescription(personaje = personaje)
                }
                ShowMoreButton(Modifier.padding(end = 10.dp), expanded)
            }
        }
        if (expanded) CardDetail(personaje = personaje, navController)
    }
}

@Composable
fun CardDetail(personaje: Character, navController: NavController) {
    Column(
        modifier = Modifier

            .padding(vertical = 10.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Origin: ${personaje.origin.name}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(text = "Location: ${personaje.location.name}",
            style = MaterialTheme.typography.bodyMedium
            )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                navController.navigate(route = AppScreens.DetailScreen.route + "/${personaje.id}")
            }) {
                Icon(imageVector = Icons.Filled.Start, contentDescription = null)
                Text(text = " More")
            }
        }
    }
}

@Composable
fun ShowMoreButton(modifier: Modifier = Modifier, expanded: Boolean) {
    //TODO CORREGIR ANIMACION NO EMPIEZA EN SENTIDO LUEGO SE EJECUTA ANIMACION
    val transition = updateTransition(expanded, label = "")
    val rotation by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 1500)
        }, label = ""
    ) { if (it) 180f else 0f }
    Icon(
        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
        contentDescription = if (expanded) "Expand Less" else "Expand More",
        modifier = modifier.rotate(rotation)
    )

}

@Composable
fun CardImage(personaje: Character) {
    AsyncImage(
        model = personaje.image,
        contentDescription = personaje.name,
        modifier = Modifier
            .wrapContentHeight()
            .size(95.dp)
    )
}

@Composable
fun CardTitle(personaje: Character, maxLines: Int = 1) {
    Text(
        text = personaje.name,
        style = MaterialTheme.typography.titleLarge,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.animateContentSize()
    )
}

@Composable
fun CardDescription(personaje: Character) {
    val statusColor = when (personaje.status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Yellow
    }
    Box() {
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
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
            Text(
                text = " ${personaje.status}", modifier = Modifier
                    .weight(1f)
                    .padding(end = 15.dp)
            )
            Text(
                text = personaje.species,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}