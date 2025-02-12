package com.example.borradorcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen (navigateToHome : () -> Unit) {
    Scaffold { inner ->

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(inner)) {

            Spacer(Modifier.weight(1f))
            Text("LOGIN", fontSize = 30.sp)
            Spacer(Modifier.weight(2f))
            Button(onClick = {navigateToHome()}) {
                Text("Navegar a la home")
            }
    }

    }
}

