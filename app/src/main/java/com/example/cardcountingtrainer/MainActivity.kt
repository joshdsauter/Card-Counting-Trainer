package com.example.cardcountingtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cardcountingtrainer.ui.screens.MainMenuScreen
import com.example.cardcountingtrainer.ui.screens.PracticeScreen
import com.example.cardcountingtrainer.ui.screens.SettingsScreen
import com.example.cardcountingtrainer.ui.theme.CardCountingTrainerTheme

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
        composable("practice") { PracticeScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
