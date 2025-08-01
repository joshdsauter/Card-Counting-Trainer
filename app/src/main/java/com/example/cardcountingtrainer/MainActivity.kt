package com.example.cardcountingtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel // Import this
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cardcountingtrainer.ui.screens.PracticeScreen
import com.example.cardcountingtrainer.ui.screens.MainMenuScreen
import com.example.cardcountingtrainer.ui.screens.CountingPracticeInstructionsScreen
import com.example.cardcountingtrainer.ui.screens.CountingPracticeScreen
import com.example.cardcountingtrainer.ui.screens.PracticeInstructionsScreen
import com.example.cardcountingtrainer.ui.screens.SettingsScreen
import com.example.cardcountingtrainer.ui.theme.CardCountingTrainerTheme
import com.example.cardcountingtrainer.viewmodel.PracticeViewModel // If PracticeViewModel is in a different package

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardCountingTrainerTheme {
                CardCountingTrainerApp()
            }
        }
    }
}

@Composable
fun CardCountingTrainerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenuScreen(
                onNavigateToCountingPractice = {
                    navController.navigate("counting_practice_instructions")
                },
                onNavigateToPractice = {
                    navController.navigate("practice_instructions")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }

        composable("counting_practice_instructions") {
            CountingPracticeInstructionsScreen(
                onContinueClicked = {
                    navController.navigate("counting_practice") {
                        popUpTo("counting_practice_instructions") { inclusive = true }
                    }
                }
            )
        }

        composable("practice_instructions") {
            PracticeInstructionsScreen(
                onContinueClicked = {
                    navController.navigate("practice") {
                        popUpTo("practice_instructions") { inclusive = true }
                    }
                }
            )
        }
        composable("counting_practice") {
            CountingPracticeScreen(navController = navController)
        }
        composable("practice") {
            val practiceViewModel: PracticeViewModel = viewModel()
            PracticeScreen(navController = navController, practiceViewModel = practiceViewModel)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}