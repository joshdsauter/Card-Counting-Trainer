package com.example.cardcountingtrainer.ui.screens // Or your appropriate UI package

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountingPracticeInstructionsScreen(
    navController: NavHostController,
    onContinueClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text("Hi-Lo Card Counting Guide" /* or stringResource(R.string.hilo_guide_title) */) }
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
                text = "Learn Hi-Lo Card Counting",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            InstructionItem(
                title = "What is Card Counting?",
                description = "Card counting is a blackjack strategy used to determine whether the next hand is likely to give an advantage to the player or dealer. It involves keeping a 'running count' based on the values of cards dealt." // or stringResource(R.string.what_is_card_counting_description)
            )

            InstructionItem(
                title = "The Hi-Lo System",
                description = "Hi-Lo is the most common card counting system. Each card is assigned a value:" // or stringResource(R.string.hilo_system_description)
            )

            InstructionItem(
                title = "Low Cards (2, 3, 4, 5, 6)",
                description = "Value: +1 (Plus One)\nThese cards increase the proportion of high cards remaining in the deck, which is good for the player." // or stringResource(R.string.low_cards_description)
            )

            InstructionItem(
                title = "Neutral Cards (7, 8, 9)",
                description = "Value: 0 (Zero)\nThese cards are considered neutral and do not change the running count." // or stringResource(R.string.neutral_cards_description)
            )

            InstructionItem(
                title = "High Cards (10, Jack, Queen, King, Ace)",
                description = "Value: -1 (Minus One)\nThese cards decrease the proportion of high cards remaining, which is less favorable for the player." // or stringResource(R.string.high_cards_description)
            )

            InstructionItem(
                title = "Keeping the Running Count",
                description = "Start with a count of 0 when the shoe (deck of cards) is new. As each card is dealt face up, you update your running count based on its Hi-Lo value (+1, 0, or -1).\nExample: If a 5 (+1), a King (-1), and an 8 (0) are dealt, the running count is +1 -1 + 0 = 0." // or stringResource(R.string.running_count_description)
            )

            InstructionItem(
                title = "Practice Goal",
                description = "On the next screen, cards will be shown one by one. Your goal is to keep an accurate running count. This screen focuses *only* on the counting, not on betting or playing strategy based on the count." // or stringResource(R.string.practice_goal_counting_description)
            )

            Button(
                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp)
            ) {
                Text("Got it, Let's Count!")
            }
        }
    }
}
