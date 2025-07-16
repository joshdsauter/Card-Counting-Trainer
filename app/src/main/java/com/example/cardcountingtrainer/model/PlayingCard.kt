// In model/PlayingCard.kt
package com.example.cardcountingtrainer.model

import androidx.annotation.DrawableRes
import com.example.cardcountingtrainer.R // Import your R file

data class PlayingCard(
    val suit: String, // e.g., "HEARTS", "DIAMONDS", "CLUBS", "SPADES" - Still here but not used for image
    val value: String // e.g., "A", "K", "10", "7"
) {
    // Convenience function to get the strategy value
    fun getStrategyValue(): Int = com.example.cardcountingtrainer.core.getStrategyCardValue(this.value)

    // Convenience function to get the rank for pairing
    fun getRank(): String = com.example.cardcountingtrainer.core.getCardRank(this.value)

    @DrawableRes
    fun getDrawableResId(): Int {
        // Simplified: Uses only the card value for the image name
        return when (value.uppercase()) {
            "A" -> R.drawable.ace // Assumes R.drawable.ace.png
            "K" -> R.drawable.king
            "Q" -> R.drawable.queen
            "J" -> R.drawable.jack
            "10" -> R.drawable.card_10 // Assuming you name number files like card_10.png, card_2.png
            "9" -> R.drawable.card_9
            "8" -> R.drawable.card_8
            "7" -> R.drawable.card_7
            "6" -> R.drawable.card_6
            "5" -> R.drawable.card_5
            "4" -> R.drawable.card_4
            "3" -> R.drawable.card_3
            "2" -> R.drawable.card_2
            else -> R.drawable.card_back // Fallback, should ideally not be hit if values are correct
        }
    }

    override fun toString(): String {
        // You can simplify this too if suits are truly ignored for display
        // return value // For example
        return "$value of $suit" // Or keep it if suit info is useful elsewhere
    }
}

// Helper for the card back drawable - REMAINS THE SAME
@DrawableRes
fun getCardBackDrawableRes(): Int {
    return R.drawable.card_back
}
