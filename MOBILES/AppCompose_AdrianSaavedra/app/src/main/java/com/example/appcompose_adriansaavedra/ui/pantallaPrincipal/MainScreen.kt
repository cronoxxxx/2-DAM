package com.example.appcompose_adriansaavedra.ui.pantallaPrincipal

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.appcompose_adriansaavedra.R
import com.example.appcompose_adriansaavedra.ui.theme.Font7f

@Composable
fun BasketballScoreboard() {

    val context = LocalContext.current
    val resources = context.resources

    var team1Fouls by rememberSaveable {
        mutableIntStateOf(resources.getInteger(R.integer.initial_fouls))
    }
    var team2Fouls by rememberSaveable {
        mutableIntStateOf(resources.getInteger(R.integer.initial_fouls))
    }
    var team1Score by rememberSaveable {
        mutableIntStateOf(resources.getInteger(R.integer.initial_team1_score))
    }
    var team2Score by rememberSaveable {
        mutableIntStateOf(resources.getInteger(R.integer.initial_team2_score))
    }

    Scaffold(containerColor = Color.Black) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopSection()
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    BoxActionsPortrait(
                        team1Score,
                        team2Score,
                        onTeam1ScoreChange = { team1Score += it },
                        onTeam2ScoreChange = { team2Score += it }
                    )
                    FoulsSectionPortrait(
                        team1Fouls,
                        team2Fouls,
                        onTeam1FoulsChange = { team1Fouls += it },
                        onTeam2FoulsChange = { team2Fouls += it }
                    )
                } else {
                    BoxActionsLandscape(
                        team1Score,
                        team2Score,
                        onTeam1ScoreChange = { team1Score += it },
                        onTeam2ScoreChange = { team2Score += it }
                    )
                    FoulsSectionLandscape(
                        team1Fouls,
                        team2Fouls,
                        onTeam1FoulsChange = { team1Fouls += it },
                        onTeam2FoulsChange = { team2Fouls += it }
                    )
                }
            }
        }
    }
}


@Composable
fun BoxActionsPortrait(
    team1Score: Int,
    team2Score: Int,
    onTeam1ScoreChange: (Int) -> Unit,
    onTeam2ScoreChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TeamScoreSection(stringResource(R.string.team1_name), team1Score, onTeam1ScoreChange)
        VersusText(modifier = Modifier.align(Alignment.CenterHorizontally))
        TeamScoreSection(stringResource(R.string.team2_name), team2Score, onTeam2ScoreChange)
    }
}

@Composable
fun BoxActionsLandscape(
    team1Score: Int,
    team2Score: Int,
    onTeam1ScoreChange: (Int) -> Unit,
    onTeam2ScoreChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.landscape_horizontal_padding)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamScoreSectionLandscapeOne(
            stringResource(R.string.team1_name),
            team1Score,
            onTeam1ScoreChange
        )
        VersusText(modifier = Modifier.align(Alignment.CenterVertically))
        TeamScoreSectionLandscapeTwo(
            stringResource(R.string.team2_name),
            team2Score,
            onTeam2ScoreChange
        )
    }

}

@Composable
fun VersusText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.versus),
        color = Color.White,
        style = Font7f,
        fontSize = dimensionResource(id = R.dimen.sixty_text_size).value.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun TeamScoreSection(
    teamName: String,
    score: Int,
    onScoreChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.second_standard_padding))
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.twelve_padding))
        ) {
            ThreeButtons(onScoreChange)
        }
        Column(modifier = Modifier.align(Alignment.Center)) {

            NamePointTeam(
                teamName,
                dimensionResource(R.dimen.tf_text_size).value.sp,
                dimensionResource(R.dimen.oht_text_size).value.sp,
                score
            )
        }
    }
}

@Composable
fun TeamScoreSectionLandscapeOne(
    teamName: String,
    score: Int,
    onScoreChange: (Int) -> Unit
) {

    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.standard_padding))) {
        ThreeButtons(onScoreChange)
    }
    NamePointTeam(
        teamName,
        dimensionResource(R.dimen.tf_text_size).value.sp,
        dimensionResource(R.dimen.oht_text_size).value.sp,
        score
    )


}

@Composable
private fun ThreeButtons(onScoreChange: (Int) -> Unit) {
    ActionButton(text = stringResource((R.string.increment_1)), onClick = { onScoreChange(1) })
    ActionButton(text = stringResource((R.string.increment_2)), onClick = { onScoreChange(2) })
    ActionButton(text = stringResource((R.string.increment_3)), onClick = { onScoreChange(3) })
}

@Composable
fun TeamScoreSectionLandscapeTwo(
    teamName: String,
    score: Int,
    onScoreChange: (Int) -> Unit
) {
    NamePointTeam(
        teamName,
        dimensionResource(R.dimen.tf_text_size).value.sp,
        dimensionResource(R.dimen.oht_text_size).value.sp,
        score
    )
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.standard_padding))) {
        ThreeButtons(onScoreChange)
    }


}

@Composable
private fun NamePointTeam(teamName: String, textSize: TextUnit, puntSize: TextUnit, score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            teamName,
            color = Color.White,
            fontSize = textSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$score",
            color = Color.Green,
            fontSize = puntSize,
            style = Font7f,
        )

    }
}

@Composable
fun FoulsSectionPortrait(
    team1Fouls: Int,
    team2Fouls: Int,
    onTeam1FoulsChange: (Int) -> Unit,
    onTeam2FoulsChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.fouls),
            color = Color.White,
            fontSize = dimensionResource(R.dimen.thirty_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FoulCounterOne(team1Fouls, onIncrement = { onTeam1FoulsChange(1) })
            VersusFoulText(modifier = Modifier.align(Alignment.CenterVertically))
            FoulCounterTwo(team2Fouls, onIncrement = { onTeam2FoulsChange(1) })
        }
        ActionButtonsPortrait()
    }
}

@Composable
fun FoulsSectionLandscape(
    team1Fouls: Int,
    team2Fouls: Int,
    onTeam1FoulsChange: (Int) -> Unit,
    onTeam2FoulsChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.landscape_horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.fouls),
            color = Color.White,
            fontSize = dimensionResource(R.dimen.thirty_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier.fillMaxWidth(),

            ) {
            TextButton(
                text = stringResource(R.string.share),
                applyModifier = true,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(modifier = Modifier.align(Alignment.Center)) {
                FoulCounterOne(team1Fouls, onIncrement = { onTeam1FoulsChange(1) })
                VersusFoulText(modifier = Modifier.align(Alignment.CenterVertically))
                FoulCounterTwo(team2Fouls, onIncrement = { onTeam2FoulsChange(1) })
            }
        }
    }
}

@Composable
fun VersusFoulText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.versus),
        color = Color.White,
        style = Font7f,
        fontSize = dimensionResource(id = R.dimen.forty_text_size).value.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        contentPadding = PaddingValues(dimensionResource(R.dimen.action_button_padding))

    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.action_btn_text_size).value.sp
        )
    }
}


@Composable
fun TopSection() {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.second_standard_padding)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopBarMainSession()
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(R.dimen.landscape_horizontal_padding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TopBarMainSession(hasSpacer = true)
            TextButton(stringResource(R.string.edit))
        }
    }


}

@Composable
private fun TopBarMainSession(hasSpacer: Boolean = false) {
    Icon(
        painter = painterResource(R.drawable.btn_play),
        contentDescription = null,
        tint = Color.White
    )
    if (hasSpacer) {
        Spacer(modifier = Modifier.padding(end = dimensionResource(R.dimen.landscape_horizontal_padding)))
    }
    Text(
        text = stringResource(R.string.timer),
        color = Color.Red,
        fontSize = dimensionResource(R.dimen.timer_size).value.sp, style = Font7f
    )
    Text(
        text = stringResource(R.string.period),
        color = Color.White,
        fontSize = dimensionResource(R.dimen.period_size).value.sp,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun FoulCounterTwo(fouls: Int, onIncrement: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = fouls.toString(),
            color = Color.Yellow,
            fontSize = dimensionResource(R.dimen.foul_counter_text_size).value.sp,
            style = Font7f,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.standard_padding))
        )
        Button(
            onClick = onIncrement,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(dimensionResource(R.dimen.standard_padding)),
        ) {
            Text(
                stringResource(R.string.increment),
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.thirty_text_size).value.sp
            )
        }
    }
}

@Composable
fun FoulCounterOne(fouls: Int, onIncrement: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onIncrement,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(dimensionResource(R.dimen.standard_padding)),
        ) {
            Text(
                stringResource(R.string.increment),
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.thirty_text_size).value.sp
            )
        }
        Text(
            text = fouls.toString(),
            color = Color.Yellow,
            fontSize = dimensionResource(R.dimen.foul_counter_text_size).value.sp,
            style = Font7f,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.standard_padding))
        )
    }
}


@Composable
fun ActionButtonsPortrait() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.standard_padding)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton((stringResource(R.string.edit)))
        TextButton((stringResource(R.string.share)))
    }
}

@Composable
private fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    applyModifier: Boolean = false
) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        shape = RoundedCornerShape(dimensionResource(R.dimen.figures_standard)),
        modifier = if (applyModifier) modifier else Modifier
    ) {
        Text(text, fontSize = dimensionResource(R.dimen.text_btn_size).value.sp)
    }
}


@PreviewScreenSizes
@Composable
fun BasketballScoreboardPreview() {
    MaterialTheme {
        BasketballScoreboard()
    }
}