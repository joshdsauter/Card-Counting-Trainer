package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainMenuScreen(
    onNavigateToCountingPractice: () -> Unit,
    onNavigateToPractice: () -> Unit, // Added: Callback for practice navigation
    onNavigateToSettings: () -> Unit  // Added: Callback for settings navigation
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Card Counting Trainer",
                style = MaterialTheme.typography.headlineLarge
            )
            Button(onClick = onNavigateToPractice) { // Changed: Use the callback
                Text("Blackjack Strategy Practice")
            }
            Button(onClick = onNavigateToCountingPractice) {
                Text("Card counting practice")
            }
            Button(onClick = onNavigateToSettings) { // Changed: Use the callback
                Text("Settings")
            }
        }
    }
}
