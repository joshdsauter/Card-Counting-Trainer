package com.example.cardcountingtrainer.model

data class PlayingCard(
    val suit: String, // e.g., "HEARTS", "SPADES"
    val value: String // e.g., "A", "K", "10", "7"
    // val imageResId: Int? = null // Optional: for displaying card images
) {
    // Convenience function to get the strategy value
    fun getStrategyValue(): Int = com.example.cardcountingtrainer.core.getStrategyCardValue(this.value)

    // Convenience function to get the rank for pairing
    fun getRank(): String = com.example.cardcountingtrainer.core.getCardRank(this.value)

    override fun toString(): String {
        return "$value of $suit"
    }
}