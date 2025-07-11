package com.example.cardcountingtrainer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Practice") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp), // Add padding around the content
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between elements
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
            // Add more instructions as needed

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            Button(
                onClick = {
                    // TODO: Implement navigation to the actual practice game or next step
                },
                modifier = Modifier.fillMaxWidth() // Make button take full width
            ) {
                Text("Start Practice")
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

@Preview(showBackground = true)
@Composable
fun PracticeScreenPreview() {
    // It's good practice to have a NavController for previews,
    // even if it's a dummy one like rememberNavController()
    PracticeScreen(navController = rememberNavController())
}
