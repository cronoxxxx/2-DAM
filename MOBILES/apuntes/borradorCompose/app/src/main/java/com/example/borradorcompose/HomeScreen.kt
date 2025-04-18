package com.example.borradorcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen  (navigateToDetail : (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    var counter by rememberSaveable{ mutableIntStateOf(0) }
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(padding)
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "kaka"
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        painter = painterResource(id = R.drawable.fav),
                        contentDescription = "Favorito",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.clickable { counter++ }
                    )
                    Text(
                        text = counter.toString(),
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }

                Text(
                    text = "Hello World!",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    Text(text = "Juego: a", color = Color.White)
                    Text(text = "Juego: Roblox", color = Color.White)
                    Text(text = "Juego: Roblox", color = Color.White)
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Hello World!",
                        modifier = Modifier.align(Alignment.TopStart),
                        color = Color.White
                    )


                }
                TextField(value=text, onValueChange = {text = it})
                //aqui mandamos el texto a detalle que recibiremos en navigation
                Button(onClick = {navigateToDetail(text)}) {
                    Text(text = "Navegar pasando un id")
                }
            }
        })


}