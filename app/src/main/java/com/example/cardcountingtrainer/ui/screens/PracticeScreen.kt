package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(navController: NavHostController) {
    var practiceStarted by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (practiceStarted) "Practice Game" else "Practice") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (practiceStarted) {
                            practiceStarted = false // Go back to instructions
                        } else {
                            navController.popBackStack() // Go back to main menu
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (!practiceStarted) {
            InstructionsContent(
                modifier = Modifier.padding(innerPadding),
                onStartPractice = { practiceStarted = true }
            )
        } else {
            PracticeGameContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun InstructionsContent(modifier: Modifier = Modifier, onStartPractice: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Blackjack Practice Instructions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        InstructionItem("Hit:", "Request another card from the dealer.")
        InstructionItem("Stand:", "Keep your current hand and end your turn.")
        InstructionItem("Double Down:", "Double your initial bet and receive only one more card.")
        InstructionItem("Split:", "If you have two cards of the same value, you can split them into two separate hands. This also requires an additional bet equal to your initial bet.")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStartPractice,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Practice")
        }
    }
}

@Composable
fun PracticeGameContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Distribute space
    ) {
        // Dealer's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Dealer's Hand", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PlayingCard(value = "A", isFaceDown = false) // Dealer's up card
                PlayingCard(isFaceDown = true)             // Dealer's hole card
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) // Space between dealer and player

        // Player's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Your Hand", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PlayingCard(value = "A")
                PlayingCard(value = "A")
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(text = "Hit")
                ActionButton(text = "Stand")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(text = "Double Down")
                ActionButton(text = "Split")
            }
        }
    }
}

@Composable
fun InstructionItem(action: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = action, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun PlayingCard(value: String = "?", suit: String = "♠", isFaceDown: Boolean = false) {
    Card(
        modifier = Modifier.size(width = 70.dp, height = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isFaceDown) Color.DarkGray else MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!isFaceDown) {
                Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                // In a real app, you might show suit icons too
            } else {
                Text(text = "Card", fontSize = 16.sp, color = Color.White) // Placeholder for back of card
            }
        }
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier.widthIn(min = 120.dp) // Ensure buttons have a decent minimum width
    ) {
        Text(text)
    }
}


@Preview(showBackground = true, name = "Instructions View")
@Composable
fun PracticeScreenInstructionsPreview() {
    PracticeScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Practice Game View")
@Composable
fun PracticeScreenGamePreview() {
    // To preview the game state, we need to simulate 'practiceStarted = true'
    // We can't directly modify the state from here in a simple preview.
    // One way is to create a wrapper or a specific preview function.
    // For simplicity here, you can manually set 'practiceStarted' to true
    // in the PracticeScreen composable when you want to preview this state.
    // Or, more robustly:
    val navController = rememberNavController()
    var practiceStarted by rememberSaveable { mutableStateOf(true) } // Simulate started state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (practiceStarted) "Practice Game" else "Practice") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (practiceStarted) {
                            practiceStarted = false
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (!practiceStarted) {
            InstructionsContent(
                modifier = Modifier.padding(innerPadding),
                onStartPractice = { practiceStarted = true }
            )
        } else {
            PracticeGameContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

