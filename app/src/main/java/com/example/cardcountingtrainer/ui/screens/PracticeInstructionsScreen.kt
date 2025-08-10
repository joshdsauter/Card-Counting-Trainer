package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Import this
import androidx.compose.foundation.verticalScroll // Import this
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeInstructionsScreen(
    onContinueClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How to Practice") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Blackjack Practice Guide",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InstructionItem(
                title = "Goal",
                description = "The goal is to get a hand total closer to 21 than the dealer, without going over 21 (busting)."
            )

            InstructionItem(
                title = "Card Values",
                description = "Number cards are their face value. Face cards (King, Queen, Jack) are 10. Aces can be 1 or 11."
            )

            Text(
                text = "Player Actions:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            InstructionItem(
                title = "Hit",
                description = "Take another card. You can hit as many times as you want, but if your total exceeds 21, you bust and lose."
            )

            InstructionItem(
                title = "Stand",
                description = "Keep your current hand and end your turn. The dealer then plays their hand."
            )

            InstructionItem(
                title = "Double Down",
                description = "Double your initial bet and receive only one more card. This is often a good move when your first two cards total 9, 10, or 11."
            )

            InstructionItem(
                title = "Split (Pairs)",
                description = "If your first two cards are a pair (e.g., two 8s), you can split them into two separate hands. Each hand gets a new second card, and you play them independently. A second bet equal to your first is placed on the new hand."
            )
            InstructionItem(
                title = "Practice Screen",
                description = "On the next screen, you'll be shown a player hand and a dealer up-card. Your task is to choose the correct action (Hit, Stand, Double Down, or Split) based on Basic Blackjack Strategy."
            )

            Button(
                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp)
            ) {
                Text("Got it, Let's Practice!" )
            }
        }
    }
}

@Composable
fun InstructionItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = description,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )
        Divider(modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeInstructionsScreenPreview() {
    PracticeInstructionsScreen(onContinueClicked = {})
}
