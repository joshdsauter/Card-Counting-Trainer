package com.example.cardcountingtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cardcountingtrainer.ui.theme.CardCountingTrainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardCountingTrainerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CardCountingTrainerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CardCountingTrainerApp(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        // Your content
    }
}
