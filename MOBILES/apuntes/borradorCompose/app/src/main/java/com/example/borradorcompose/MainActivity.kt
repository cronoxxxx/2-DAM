package com.example.borradorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    @Preview(showBackground = true, showSystemUi = true, device = Devices.PHONE)
    @Composable
    fun App() {
        var counter by rememberSaveable() { mutableIntStateOf(0) }
        Scaffold(
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(padding)
                ) {
                    //el maxwidth es como el match parent
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


                }
            })


    }

}