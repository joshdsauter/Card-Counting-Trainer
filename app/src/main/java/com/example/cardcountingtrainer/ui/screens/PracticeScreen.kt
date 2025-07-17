package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import com.example.cardcountingtrainer.ui.components.TextCardView
import com.example.cardcountingtrainer.viewmodel.PracticeViewModel

// In ui/PracticeScreen.kt
// Import your new TextCardView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(practiceViewModel: PracticeViewModel = viewModel()) {
    val uiState = practiceViewModel.uiState
    val cardWidth = 65.dp // Adjust as needed for text cards

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
        ) {
            ScoreDisplay(
                score = uiState.score,
                totalAttempts = uiState.totalAttempts,
                accuracy = uiState.accuracy
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Dealer's Hand", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.dealerCard != null) {
                    TextCardView( // USE TextCardView HERE
                        cardValue = uiState.dealerCard.value, // Pass the string value
                        isFaceUp = true,
                        cardWidth = cardWidth
                    )
                    TextCardView( // And HERE for the card back
                        cardValue = null, // Or uiState.dealerCard.value, will be hidden by isFaceUp
                        isFaceUp = false,
                        cardWidth = cardWidth
                    )
                } else {
                    // Placeholder (same aspect ratio as card)
                    Box(
                        modifier = Modifier
                            .width(cardWidth)
                            .aspectRatio(2.5f / 3.5f)
                            .padding(horizontal = 4.dp)
                    ) // Maintain spacing
                    Box(
                        modifier = Modifier
                            .width(cardWidth)
                            .aspectRatio(2.5f / 3.5f)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text("Your Hand", style = MaterialTheme.typography.titleMedium)
            if (uiState.playerCards.isNotEmpty()) {
                Text(
                    "Total: ${uiState.playerSum} (${uiState.handType.name})${if (uiState.isPair) " - Pair" else ""}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.playerCards.isNotEmpty()) {
                    uiState.playerCards.forEach { playerCard ->
                        TextCardView( // USE TextCardView HERE
                            cardValue = playerCard.value, // Pass the string value
                            isFaceUp = true,
                            cardWidth = cardWidth
                        )
                    }
                } else {
                    // Placeholder if player cards are not yet available
                    Box(modifier = Modifier.height(cardWidth * (3.5f / 2.5f) + 8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            if (uiState.correctAnswer != null) {
                ActionButtons(
                    onHit = { practiceViewModel.submitAnswer(StrategyAction.HIT) },
                    onStand = { practiceViewModel.submitAnswer(StrategyAction.STAND) },
                    onDouble = { practiceViewModel.submitAnswer(StrategyAction.DOUBLE_DOWN) },
                    onSplit = { practiceViewModel.submitAnswer(StrategyAction.SPLIT) },
                    canSplit = uiState.isPair
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            uiState.message?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = if (it.startsWith("Correct")) FontWeight.Bold else FontWeight.Normal,
                    color = if (it.startsWith("Correct")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { practiceViewModel.generateNewHand() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next Hand")
            }
        }
    }
}

// ScoreDisplay composable (remains the same)
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

// ActionButtons composable (remains the same)
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