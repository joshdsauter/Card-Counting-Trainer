package com.example.cardcountingtrainer.core

/**
 * Represents the type of hand total for the player.
 * - HARD: No Ace is counted as 11, or all Aces are counted as 1.
 * - SOFT: An Ace is currently counted as 11.
 */
enum class HandType {
    HARD,
    SOFT
}

/**
 * Represents the recommended action based on basic strategy.
 */
enum class StrategyAction {
    HIT,
    STAND,
    DOUBLE_DOWN, // Or HIT if Double Down is not allowed
    SPLIT
}

/**
 * Represents the key for the Blackjack strategy lookup.
 *
 * @param playerTotal The sum of the player's cards.
 * @param dealerUpCard The value of the dealer's face-up card (2-10, or 11 for Ace).
 * @param handType Whether the player's total is HARD or SOFT.
 * @param isPair True if the player's hand is a pair eligible for splitting, false otherwise.
 */
data class StrategyKey(
    val playerTotal: Int,
    val dealerUpCard: Int, // Use 11 for Ace for simplicity in lookup
    val handType: HandType,
    val isPair: Boolean = false // Default to false for non-pair hands
)

/**
 * Basic Blackjack Strategy Chart.
 *
 * Key: StrategyKey(playerTotal, dealerUpCardValue, HandType)
 *      - playerTotal: Sum of player's cards.
 *      - dealerUpCardValue: 2-9, 10 (for 10, J, Q, K), 11 (for Ace).
 *      - HandType: HARD or SOFT.
 * Value: StrategyAction (HIT, STAND, DOUBLE_DOWN, SPLIT)
 *
 * This is a simplified example. A complete strategy chart is extensive.
 * You'll need to populate this thoroughly based on a reliable Blackjack basic strategy chart.
 *
 * Example sources:
 * - https://www.blackjackapprenticeship.com/blackjack-strategy-charts/
 * - https://wizardofodds.com/games/blackjack/strategy/4-decks/
 */

val basicStrategyChart: Map<StrategyKey, StrategyAction> = mutableMapOf<StrategyKey, StrategyAction>().apply {
    // --- Hard Totals (Player has no Ace, or Ace counts as 1) ---
    // Player Total 5-8 (Always Hit)
    for (total in 5..8) {
        for (dealer in 2..11) {
            put(StrategyKey(total, dealer, HandType.HARD, isPair = false), StrategyAction.HIT)
        }
    }

    // Player Total 9
    put(StrategyKey(9, 2, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(9, 3, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(9, 4, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(9, 5, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(9, 6, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(9, 7, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(9, 8, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(9, 9, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(9, 10, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(9, 11, HandType.HARD, isPair = false), StrategyAction.HIT)

    // Player Total 10
    for (dealer in 2..9) {
        put(StrategyKey(10, dealer, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    }
    put(StrategyKey(10, 10, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(10, 11, HandType.HARD, isPair = false), StrategyAction.HIT)

    // Player Total 11
    for (dealer in 2..10) {
        put(StrategyKey(11, dealer, HandType.HARD, isPair = false), StrategyAction.DOUBLE_DOWN)
    }
    put(StrategyKey(11, 11, HandType.HARD, isPair = false), StrategyAction.HIT)

    // Player Total 12
    put(StrategyKey(12, 2, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 3, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 4, HandType.HARD, isPair = false), StrategyAction.STAND)
    put(StrategyKey(12, 5, HandType.HARD, isPair = false), StrategyAction.STAND)
    put(StrategyKey(12, 6, HandType.HARD, isPair = false), StrategyAction.STAND)
    put(StrategyKey(12, 7, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 8, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 9, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 10, HandType.HARD, isPair = false), StrategyAction.HIT)
    put(StrategyKey(12, 11, HandType.HARD, isPair = false), StrategyAction.HIT)

    // Player Total 13-16
    for (total in 13..16) {
        for (dealer in 2..6) {
            put(StrategyKey(total, dealer, HandType.HARD, isPair = false), StrategyAction.STAND)
        }
        for (dealer in 7..11) {
            put(StrategyKey(total, dealer, HandType.HARD, isPair = false), StrategyAction.HIT)
        }
    }

    // Player Total 17+
    for (total in 17..21) {
        for (dealer in 2..11) {
            put(StrategyKey(total, dealer, HandType.HARD, isPair = false), StrategyAction.STAND)
        }
    }

    // --- Soft Totals ---
    // Player Soft 13 (A,2)
    put(StrategyKey(13, 2, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 3, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 4, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(13, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(13, 7, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 8, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(13, 11, HandType.SOFT, isPair = false), StrategyAction.HIT)

    // Player Soft 14 (A,3)
    put(StrategyKey(14, 2, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 3, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 4, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(14, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(14, 7, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 8, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(14, 11, HandType.SOFT, isPair = false), StrategyAction.HIT)

    // Player Soft 15 (A,4)
    put(StrategyKey(15, 2, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 3, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 4, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(15, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(15, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(15, 7, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 8, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(15, 11, HandType.SOFT, isPair = false), StrategyAction.HIT)

    // Player Soft 16 (A,5)
    put(StrategyKey(16, 2, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 3, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 4, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(16, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(16, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(16, 7, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 8, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(16, 11, HandType.SOFT, isPair = false), StrategyAction.HIT)

    // Player Soft 17 (A,6)
    put(StrategyKey(17, 2, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(17, 3, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(17, 4, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(17, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(17, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(17, 7, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(17, 8, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(17, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(17, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(17, 11, HandType.SOFT, isPair = false), StrategyAction.HIT)

    // Player Soft 18 (A,7)
    put(StrategyKey(18, 2, HandType.SOFT, isPair = false), StrategyAction.STAND)
    put(StrategyKey(18, 3, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(18, 4, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(18, 5, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(18, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    put(StrategyKey(18, 7, HandType.SOFT, isPair = false), StrategyAction.STAND)
    put(StrategyKey(18, 8, HandType.SOFT, isPair = false), StrategyAction.STAND)
    put(StrategyKey(18, 9, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(18, 10, HandType.SOFT, isPair = false), StrategyAction.HIT)
    put(StrategyKey(18, 11, HandType.SOFT, isPair = false), StrategyAction.STAND)

    // Player Soft 19 (A,8)
    put(StrategyKey(19, 6, HandType.SOFT, isPair = false), StrategyAction.DOUBLE_DOWN)
    (listOf(2,3,4,5) + (7..11)).forEach { dealer ->
        put(StrategyKey(19, dealer, HandType.SOFT, isPair = false), StrategyAction.STAND)
    }

    // Player Soft 20 (A,9)
    for (dealer in 2..11) {
        put(StrategyKey(20, dealer, HandType.SOFT, isPair = false), StrategyAction.STAND)
    }

    // Player Soft 21 (A,10) - Blackjack
    for (dealer in 2..11) {
        put(StrategyKey(21, dealer, HandType.SOFT, isPair = false), StrategyAction.STAND)
    }

    // --- Pairs ---
    // Pair of Aces (A,A) - Player Total 12 Soft, Always Split
    for (dealer in 2..11) {
        put(StrategyKey(12, dealer, HandType.SOFT, isPair = true), StrategyAction.SPLIT)
    }

    // Pair of 2s (Total 4, Hard)
    for (dealer in 2..7) { put(StrategyKey(4, dealer, HandType.HARD, isPair = true), StrategyAction.SPLIT) }
    for (dealer in 8..11) { put(StrategyKey(4, dealer, HandType.HARD, isPair = true), StrategyAction.HIT) }

    // Pair of 3s (Total 6, Hard)
    for (dealer in 2..7) { put(StrategyKey(6, dealer, HandType.HARD, isPair = true), StrategyAction.SPLIT) }
    for (dealer in 8..11) { put(StrategyKey(6, dealer, HandType.HARD, isPair = true), StrategyAction.HIT) }

    // Pair of 4s (Total 8, Hard)
    put(StrategyKey(8, 2, HandType.HARD, isPair = true), StrategyAction.HIT)
    put(StrategyKey(8, 3, HandType.HARD, isPair = true), StrategyAction.HIT)
    put(StrategyKey(8, 4, HandType.HARD, isPair = true), StrategyAction.HIT)
    put(StrategyKey(8, 5, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(8, 6, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    for (dealer in 7..11) { put(StrategyKey(8, dealer, HandType.HARD, isPair = true), StrategyAction.HIT) }


    // Pair of 5s (Total 10, Hard) - Treat as Hard 10
    for (dealer in 2..9) { put(StrategyKey(10, dealer, HandType.HARD, isPair = true), StrategyAction.DOUBLE_DOWN) }
    put(StrategyKey(10, 10, HandType.HARD, isPair = true), StrategyAction.HIT)
    put(StrategyKey(10, 11, HandType.HARD, isPair = true), StrategyAction.HIT)

    // Pair of 6s (Total 12, Hard)
    for (dealer in 2..6) { put(StrategyKey(12, dealer, HandType.HARD, isPair = true), StrategyAction.SPLIT) }
    for (dealer in 7..11) { put(StrategyKey(12, dealer, HandType.HARD, isPair = true), StrategyAction.HIT) }

    // Pair of 7s (Total 14, Hard)
    for (dealer in 2..7) { put(StrategyKey(14, dealer, HandType.HARD, isPair = true), StrategyAction.SPLIT) }
    for (dealer in 8..11) { put(StrategyKey(14, dealer, HandType.HARD, isPair = true), StrategyAction.HIT) }

    // Pair of 8s (Total 16, Hard) - Always Split
    for (dealer in 2..11) {
        put(StrategyKey(16, dealer, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    }

    // Pair of 9s (Total 18, Hard)
    put(StrategyKey(18, 2, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 3, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 4, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 5, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 6, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 7, HandType.HARD, isPair = true), StrategyAction.STAND)
    put(StrategyKey(18, 8, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 9, HandType.HARD, isPair = true), StrategyAction.SPLIT)
    put(StrategyKey(18, 10, HandType.HARD, isPair = true), StrategyAction.STAND)
    put(StrategyKey(18, 11, HandType.HARD, isPair = true), StrategyAction.STAND)

    // Pair of 10s (10, J, Q, K - Total 20, Hard) - Always Stand
    for (dealer in 2..11) {
        put(StrategyKey(20, dealer, HandType.HARD, isPair = true), StrategyAction.STAND)
    }
}.toMap()

// Helper function to get card value for strategy chart (Ace = 11, Face = 10)
fun getStrategyCardValue(card: String): Int {
    return when (card.uppercase()) {
        "A" -> 11 // Ace is 11 for strategy calculation, can be 1 if total > 21
        "K", "Q", "J" -> 10
        else -> card.toIntOrNull() ?: 0
    }
}

// Helper to determine card rank for pairing (A, K, Q, J, 10, 9, ... 2)
fun getCardRank(cardValue: String): String {
    return when (cardValue.uppercase()) {
        "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" -> cardValue.uppercase()
        else -> "UNKNOWN" // Should not happen with valid card data
    }
}

