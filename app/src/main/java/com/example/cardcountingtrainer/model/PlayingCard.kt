package com.example.cardcountingtrainer.model

import com.example.cardcountingtrainer.core.getCardRank
import com.example.cardcountingtrainer.core.getStrategyCardValue

data class PlayingCard(
    val suit: String, // Still here, but won't be visually represented on the card face
    val value: String // e.g., "A", "K", "10", "7" - THIS IS WHAT WE'LL DISPLAY
) {
    fun getStrategyValue(): Int =
        getStrategyCardValue(this.value)

    fun getRank(): String = getCardRank(this.value)

    override fun toString(): String {
        return "$value of $suit"
    }
}