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
import com.example.cardcountingtrainer.ui.PracticeScreen
import com.example.cardcountingtrainer.ui.screens.MainMenuScreen
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
        composable("main_menu") { MainMenuScreen(navController) }
        composable("practice") {
            // Obtain the ViewModel instance here
            val practiceViewModel: PracticeViewModel = viewModel()
            PracticeScreen(practiceViewModel = practiceViewModel)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}