package com.example.cardcountingtrainer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cardcountingtrainer.model.PlayingCard
import com.example.cardcountingtrainer.model.getCardBackDrawableRes

@Composable
fun PlayingCardView(
    modifier: Modifier = Modifier,
    card: PlayingCard?,
    isFaceUp: Boolean = true,
    cardWidth: Dp = 80.dp
) {
    val imageRes = if (isFaceUp && card != null) {
        card.getDrawableResId() // This will now call the simplified version
    } else {
        getCardBackDrawableRes()
    }

    Card(
        modifier = modifier
            .width(cardWidth)
            .aspectRatio(2.5f / 3.5f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = if (isFaceUp && card != null) card.toString() else "Face down card",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
