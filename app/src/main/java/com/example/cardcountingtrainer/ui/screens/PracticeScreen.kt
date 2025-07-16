package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cardcountingtrainer.core.StrategyAction
import com.example.cardcountingtrainer.ui.components.PlayingCardView // Still using this
import com.example.cardcountingtrainer.viewmodel.PracticeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(practiceViewModel: PracticeViewModel = viewModel()) {
    val uiState = practiceViewModel.uiState
    val cardWidth = 75.dp // Define a common card width

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
            // Score Display (remains the same)
            ScoreDisplay(
                score = uiState.score,
                totalAttempts = uiState.totalAttempts,
                accuracy = uiState.accuracy
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Dealer's Hand (Layout remains the same, PlayingCardView handles simplified image)
            Text("Dealer's Hand", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.dealerCard != null) {
                    PlayingCardView( // Will use simplified images
                        card = uiState.dealerCard,
                        isFaceUp = true,
                        cardWidth = cardWidth
                    )
                    PlayingCardView( // Will use card_back.png
                        card = null,
                        isFaceUp = false,
                        cardWidth = cardWidth
                    )
                } else {
                    Box(modifier = Modifier.height(cardWidth * (3.5f/2.5f) + 8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Player's Hand (Layout remains the same, PlayingCardView handles simplified image)
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
                        PlayingCardView( // Will use simplified images
                            card = playerCard,
                            isFaceUp = true,
                            cardWidth = cardWidth
                        )
                    }
                } else {
                    Box(modifier = Modifier.height(cardWidth * (3.5f/2.5f) + 8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            // Action Buttons & Feedback (remains the same)
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

            // Next Hand Button (remains the same)
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