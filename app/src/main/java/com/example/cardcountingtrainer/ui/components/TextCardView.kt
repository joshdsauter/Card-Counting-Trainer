package com.example.cardcountingtrainer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// In ui/components/TextCardView.kt (create this new file and package if needed)
@Composable
fun TextCardView(
    modifier: Modifier = Modifier,
    cardValue: String?, // The value to display (e.g., "A", "K", "10", "7") or null for card back
    isFaceUp: Boolean = true,
    cardWidth: Dp = 70.dp, // Slightly smaller default for text cards might look better
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.outline
) {
    Card(
        modifier = modifier
            .width(cardWidth)
            .aspectRatio(2.5f / 3.5f), // Standard playing card aspect ratio
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp), // Padding inside the card
            contentAlignment = Alignment.Center
        ) {
            if (isFaceUp && cardValue != null) {
                Text(
                    text = cardValue.uppercase(),
                    fontSize = when (cardValue.length) { // Adjust font size for longer values like "10"
                        1 -> 24.sp
                        2 -> 20.sp
                        else -> 18.sp
                    },
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            } else {
                // For the card back, you can have a pattern or just a solid color
                // Here, we'll just show nothing, relying on the card's background.
                // You could draw a pattern here if desired.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(borderColor.copy(alpha = 0.2f)) // Subtle back pattern
                )
            }
        }
    }
}

// --- Preview for TextCardView ---
@Preview(showBackground = true, name = "Ace Card")
@Composable
fun TextCardViewAcePreview() {
    MaterialTheme {
        TextCardView(cardValue = "A", isFaceUp = true)
    }
}

@Preview(showBackground = true, name = "Ten Card")
@Composable
fun TextCardViewTenPreview() {
    MaterialTheme {
        TextCardView(cardValue = "10", isFaceUp = true)
    }
}

@Preview(showBackground = true, name = "Card Back")
@Composable
fun TextCardViewBackPreview() {
    MaterialTheme {
        TextCardView(cardValue = null, isFaceUp = false)
    }
}