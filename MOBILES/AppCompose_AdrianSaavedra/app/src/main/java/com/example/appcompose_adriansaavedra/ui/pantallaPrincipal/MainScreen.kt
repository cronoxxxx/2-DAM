package com.example.appcompose_adriansaavedra.ui.pantallaPrincipal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasketballScoreboard() {

    var team1Fouls by remember { mutableStateOf(0) }
    var team2Fouls by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TopSection()
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Start
//            ) {
////                ScoreButtons(
////                    onTeam2ScoreChange = { team2Score += it },
////                    isTeam1 = false
////                )
////                TeamScores(team1Score, team2Score)

BoxActions()
            FoulsSection(team1Fouls, team2Fouls,
                onTeam1FoulsChange = { team1Fouls += it },
                onTeam2FoulsChange = { team2Fouls += it }
            )
            ActionButtons()
        }
    }}

@Composable
fun BoxActions() {
    var team1Score by remember { mutableIntStateOf(34) }
    var team2Score by remember { mutableIntStateOf(18) }
    Box(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            ActionButton(text = "+1")
            ActionButton(text = "+2")
            ActionButton(text = "+3")
            Spacer(modifier = Modifier.height(50.dp))
            ActionButton(text = "+1")
            ActionButton(text = "+2")
            ActionButton(text = "+3")
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
            ,horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Equipo 1", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = team1Score.toString(),
                color = Color.Green,
                fontSize = 90.sp,
                fontFamily = FontFamily.Monospace
            )
            Text(
                "x",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light
            )
            Text("Equipo 2", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = team2Score.toString(),
                color = Color.Green,
                fontSize = 90.sp,
                fontFamily = FontFamily.Monospace
            )

        }
    }
}

@Composable
fun ActionButton(text: String) {
    Button(
        onClick = { /* TODO: Implement functionality */ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
    }
}


@Composable
fun TopSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play/Pause",
                tint = Color.White
            )
        }
        Text(
            text = "10:00",
            color = Color.Red,
            fontSize = 85.sp,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "1.°",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Composable
//fun TeamScores(team1Score: Int, team2Score: Int) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(horizontal = 16.dp)
//    ) {
//        Text("Equipo 1", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        Text(
//            text = team1Score.toString(),
//            color = Color.Green,
//            fontSize = 90.sp,
//            fontFamily = FontFamily.Monospace
//        )
//        Text("x", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Light)
//        Text(
//            text = team2Score.toString(),
//            color = Color.Green,
//            fontSize = 90.sp,
//            fontFamily = FontFamily.Monospace
//        )
//        Text("Equipo 2", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//    }
//}
//
//
//@Composable
//fun ScoreButtons(
//    onTeam1ScoreChange: ((Int) -> Unit)? = null,
//    onTeam2ScoreChange: ((Int) -> Unit)? = null,
//    isTeam1: Boolean
//) {
//    Column(
//        verticalArrangement = Arrangement.Top,
//        modifier = Modifier.padding(8.dp)
//    ) {
//        val onScoreChange = if (isTeam1) onTeam1ScoreChange else onTeam2ScoreChange
//        ScoreButton("+1", onClick = { onScoreChange?.invoke(1) })
//        ScoreButton("+2", onClick = { onScoreChange?.invoke(2) })
//        ScoreButton("+3", onClick = { onScoreChange?.invoke(3) })
//
//        if (!isTeam1) {
//            Spacer(modifier = Modifier.height(18.dp))
//            ScoreButton("+1", onClick = { onScoreChange?.invoke(1) })
//            ScoreButton("+2", onClick = { onScoreChange?.invoke(2) })
//            ScoreButton("+3", onClick = { onScoreChange?.invoke(3) })
//        }
//    }
//}




@Composable
fun ScoreButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
    }
}



@Composable
fun FoulsSection(
    team1Fouls: Int,
    team2Fouls: Int,
    onTeam1FoulsChange: (Int) -> Unit,
    onTeam2FoulsChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Faltas", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Box(
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FoulCounterOne(team1Fouls, onIncrement = { onTeam1FoulsChange(1) })
                FoulCounterTwo(team2Fouls, onIncrement = { onTeam2FoulsChange(1) })
            }
            Text(
                "x",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun FoulCounterTwo(fouls: Int, onIncrement: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = fouls.toString(),
            color = Color.White,
            fontSize = 55.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Button(
            onClick = onIncrement,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("+", color = Color.Black, fontSize = 20.sp)
        }
    }
}

@Composable
fun FoulCounterOne(fouls: Int, onIncrement: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onIncrement,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("+", color = Color.Black, fontSize = 20.sp)
        }
        Text(
            text = fouls.toString(),
            color = Color.White,
            fontSize = 55.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}


@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { /* TODO: Implement edit functionality */ },
            colors = ButtonDefaults.buttonColors(containerColor =Color.Blue)
        ) {
            Text("EDITAR", fontSize = 20.sp)
        }
        Button(
            onClick = { /* TODO: Implement delete functionality */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("ELIMINAR", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PHONE)
@Composable
fun BasketballScoreboardPreview() {
    MaterialTheme {
        BasketballScoreboard()
    }
}