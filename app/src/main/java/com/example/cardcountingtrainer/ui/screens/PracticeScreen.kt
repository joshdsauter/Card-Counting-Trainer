package com.example.cardcountingtrainer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cardcountingtrainer.core.StrategyAction
import com.example.cardcountingtrainer.viewmodel.PracticeViewModel

// In ui/PracticeScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(practiceViewModel: PracticeViewModel = viewModel()) {
    val uiState = practiceViewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Blackjack Strategy Practice") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Score Display
            ScoreDisplay(
                score = uiState.score,
                totalAttempts = uiState.totalAttempts,
                accuracy = uiState.accuracy
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Hand Display
            if (uiState.dealerCard != null && uiState.playerCards.isNotEmpty()) {
                HandDisplay(
                    dealerCard = uiState.dealerCard.toString(),
                    playerCards = uiState.playerCards.joinToString { it.toString() },
                    playerSum = uiState.playerSum,
                    handType = uiState.handType.name,
                    isPair = uiState.isPair
                )
            } else {
                Text("Loading hand...")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            if (uiState.correctAnswer != null) {
                ActionButtons(
                    onHit = { practiceViewModel.submitAnswer(StrategyAction.HIT) },
                    onStand = { practiceViewModel.submitAnswer(StrategyAction.STAND) },
                    onDouble = { practiceViewModel.submitAnswer(StrategyAction.DOUBLE_DOWN) },
                    onSplit = { practiceViewModel.submitAnswer(StrategyAction.SPLIT) },
                    canSplit = uiState.isPair // Only enable split if it's a pair
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Feedback Message
            uiState.message?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = if (it.startsWith("Correct")) FontWeight.Bold else FontWeight.Normal,
                    color = if (it.startsWith("Correct")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }


            Spacer(modifier = Modifier.weight(1f)) // Pushes Next Hand button to bottom if not enough content

            // Next Hand Button
            Button(
                onClick = { practiceViewModel.generateNewHand() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next Hand")
            }
        }
    }
}

@Composable
fun ScoreDisplay(score: Int, totalAttempts: Int, accuracy: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Score: $score", fontSize = 18.sp)
        Text("Total: $totalAttempts", fontSize = 18.sp)
        Text(
            text = "Accuracy: ${String.format("%.1f", accuracy)}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HandDisplay(
    dealerCard: String,
    playerCards: String,
    playerSum: Int,
    handType: String,
    isPair: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Dealer Shows: $dealerCard", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Your Hand: $playerCards", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("Total: $playerSum ($handType)${if (isPair) " - Pair" else ""}", fontSize = 18.sp)
    }
}

@Composable
fun ActionButtons(
    onHit: () -> Unit,
    onStand: () -> Unit,
    onDouble: () -> Unit,
    onSplit: () -> Unit,
    canSplit: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Button(onClick = onHit, modifier = Modifier.weight(1f)) { Text("Hit") }
            Button(onClick = onStand, modifier = Modifier.weight(1f)) { Text("Stand") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onDouble, modifier = Modifier.weight(1f)) { Text("Double") }
            Button(
                onClick = onSplit,
                enabled = canSplit,
                modifier = Modifier.weight(1f)
            ) { Text("Split") }
        }
    }
}