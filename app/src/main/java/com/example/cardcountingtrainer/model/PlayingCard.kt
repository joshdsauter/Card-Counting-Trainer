package com.example.cardcountingtrainer.model

import com.example.cardcountingtrainer.core.getCardRank
import com.example.cardcountingtrainer.core.getStrategyCardValue

// In model/PlayingCard.kt
// No changes needed from the version where getDrawableResId() was simplified
// to use only card values. We will simply not call that method for the text-based card.
data class PlayingCard(
    val suit: String, // Still here, but won't be visually represented on the card face
    val value: String // e.g., "A", "K", "10", "7" - THIS IS WHAT WE'LL DISPLAY
) {
    fun getStrategyValue(): Int =
        getStrategyCardValue(this.value)

    fun getRank(): String = getCardRank(this.value)

    // This method will NOT be used by the TextCardView, but is fine to keep
    // @DrawableRes
    // fun getDrawableResId(): Int { ... }

    override fun toString(): String {
        return "$value of $suit" // Or just "$value" if you prefer
    }
}