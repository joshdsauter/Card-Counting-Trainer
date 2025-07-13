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

// ... (HandType, StrategyAction, StrategyKey definitions remain the same)
val basicStrategyChart: Map<StrategyKey, StrategyAction> = mapOf(
    // --- Hard Totals (Player has no Ace, or Ace counts as 1) ---
    // Player Total 5-8 (Always Hit)
    *(5..8).flatMap { total ->
        (2..11).map { dealer ->
            StrategyKey(total, dealer, HandType.HARD, isPair = false) to StrategyAction.HIT
        }
    }.toTypedArray(),

    // Player Total 9
    StrategyKey(9, 2, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(9, 3, HandType.HARD, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(9, 4, HandType.HARD, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(9, 5, HandType.HARD, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(9, 6, HandType.HARD, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(9, 7, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(9, 8, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(9, 9, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(9, 10, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(9, 11, HandType.HARD, isPair = false) to StrategyAction.HIT,

    // Player Total 10
    *(2..9).map { dealer ->
        StrategyKey(
            10,
            dealer,
            HandType.HARD,
            isPair = false
        ) to StrategyAction.DOUBLE_DOWN
    }.toTypedArray(),
    StrategyKey(10, 10, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(10, 11, HandType.HARD, isPair = false) to StrategyAction.HIT,

    // Player Total 11
    *(2..10).map { dealer ->
        StrategyKey(
            11,
            dealer,
            HandType.HARD,
            isPair = false
        ) to StrategyAction.DOUBLE_DOWN
    }.toTypedArray(),
    StrategyKey(
        11,
        11,
        HandType.HARD,
        isPair = false
    ) to StrategyAction.HIT, // Some charts Double vs Ace

    // Player Total 12
    StrategyKey(12, 2, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 3, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 4, HandType.HARD, isPair = false) to StrategyAction.STAND,
    StrategyKey(12, 5, HandType.HARD, isPair = false) to StrategyAction.STAND,
    StrategyKey(12, 6, HandType.HARD, isPair = false) to StrategyAction.STAND,
    StrategyKey(12, 7, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 8, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 9, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 10, HandType.HARD, isPair = false) to StrategyAction.HIT,
    StrategyKey(12, 11, HandType.HARD, isPair = false) to StrategyAction.HIT,

    // Player Total 13-16 (Stand vs Dealer 2-6, Hit vs 7-Ace)
    *(13..16).flatMap { total ->
        (2..6).map { dealer ->
            StrategyKey(
                total,
                dealer,
                HandType.HARD,
                isPair = false
            ) to StrategyAction.STAND
        } +
                (7..11).map { dealer ->
                    StrategyKey(
                        total,
                        dealer,
                        HandType.HARD,
                        isPair = false
                    ) to StrategyAction.HIT
                }
    }.toTypedArray(),

    // Player Total 17+ (Always Stand)
    *(17..21).flatMap { total ->
        (2..11).map { dealer ->
            StrategyKey(total, dealer, HandType.HARD, isPair = false) to StrategyAction.STAND
        }
    }.toTypedArray(),

    // --- Soft Totals (Player has an Ace counted as 11) ---
    // Player Soft 13 (A,2)
    StrategyKey(13, 2, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 3, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 4, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(13, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(13, 7, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 8, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(13, 11, HandType.SOFT, isPair = false) to StrategyAction.HIT,

    // Player Soft 14 (A,3)
    StrategyKey(14, 2, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 3, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 4, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(14, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(14, 7, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 8, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(14, 11, HandType.SOFT, isPair = false) to StrategyAction.HIT,

    // Player Soft 15 (A,4)
    StrategyKey(15, 2, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 3, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 4, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(15, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(15, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(15, 7, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 8, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(15, 11, HandType.SOFT, isPair = false) to StrategyAction.HIT,

    // Player Soft 16 (A,5)
    StrategyKey(16, 2, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 3, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 4, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(16, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(16, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(16, 7, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 8, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(16, 11, HandType.SOFT, isPair = false) to StrategyAction.HIT,

    // Player Soft 17 (A,6)
    StrategyKey(17, 2, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(17, 3, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(17, 4, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(17, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(17, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN,
    StrategyKey(17, 7, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(17, 8, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(17, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(17, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(17, 11, HandType.SOFT, isPair = false) to StrategyAction.HIT,

    // Player Soft 18 (A,7)
    StrategyKey(
        18,
        2,
        HandType.SOFT,
        isPair = false
    ) to StrategyAction.STAND, // Some charts Double Down
    StrategyKey(18, 3, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN, // Or Stand
    StrategyKey(18, 4, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN, // Or Stand
    StrategyKey(18, 5, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN, // Or Stand
    StrategyKey(18, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN, // Or Stand
    StrategyKey(18, 7, HandType.SOFT, isPair = false) to StrategyAction.STAND,
    StrategyKey(18, 8, HandType.SOFT, isPair = false) to StrategyAction.STAND,
    StrategyKey(18, 9, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(18, 10, HandType.SOFT, isPair = false) to StrategyAction.HIT,
    StrategyKey(
        18,
        11,
        HandType.SOFT,
        isPair = false
    ) to StrategyAction.STAND, // Some charts Hit vs Ace

    // Player Soft 19 (A,8) (Stand vs Dealer 2-5, 7-Ace; Double vs 6)
    StrategyKey(19, 6, HandType.SOFT, isPair = false) to StrategyAction.DOUBLE_DOWN, // Or Stand
    *(listOf(2, 3, 4, 5) + (7..11)).map { dealer ->
        StrategyKey(
            19,
            dealer,
            HandType.SOFT,
            isPair = false
        ) to StrategyAction.STAND
    }.toTypedArray(),

    // Player Soft 20 (A,9) (Always Stand)
    *(2..11).map { dealer ->
        StrategyKey(
            20,
            dealer,
            HandType.SOFT,
            isPair = false
        ) to StrategyAction.STAND
    }.toTypedArray(),

    // Player Soft 21 (A,10) (Always Stand - Blackjack)
    *(2..11).map { dealer ->
        StrategyKey(
            21,
            dealer,
            HandType.SOFT,
            isPair = false
        ) to StrategyAction.STAND
    }.toTypedArray(),


    // --- Pairs (Player has two cards of the same rank) ---
    // Pair of Aces (A,A) - Player Total 12 Soft, Always Split
    *(2..11).map { dealer ->
        StrategyKey(
            12,
            dealer,
            HandType.SOFT,
            isPair = true
        ) to StrategyAction.SPLIT
    }.toTypedArray(), // A,A represented as Soft 12

    // Pair of 2s (Total 4, Hard)
    StrategyKey(4, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 3s (Total 6, Hard)
    StrategyKey(6, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(6, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(6, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(6, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 4s (Total 8, Hard)
    StrategyKey(8, 2, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 3, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 4, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(
        8,
        5,
        HandType.HARD,
        isPair = true
    ) to StrategyAction.SPLIT, // Split only vs dealer 5,6
    StrategyKey(8, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(8, 7, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(8, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 5s (Total 10, Hard) - Treat as a Hard 10 (Double, except vs 10, Ace)
    *(2..9).map { dealer ->
        StrategyKey(
            10,
            dealer,
            HandType.HARD,
            isPair = true
        ) to StrategyAction.DOUBLE_DOWN
    }.toTypedArray(),
    StrategyKey(10, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(10, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 6s (Total 12, Hard)
    StrategyKey(12, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 7, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(12, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(12, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(12, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(12, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 7s (Total 14, Hard)
    StrategyKey(14, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(14, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(14, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(14, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(14, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 8s (Total 16, Hard) - Always Split
    *(2..11).map { dealer ->
        StrategyKey(
            16,
            dealer,
            HandType.HARD,
            isPair = true
        ) to StrategyAction.SPLIT
    }.toTypedArray(),

    // Pair of 9s (Total 18, Hard)
    StrategyKey(18, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 7, HandType.HARD, isPair = true) to StrategyAction.STAND, // Stand vs 7
    StrategyKey(18, 8, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 9, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(18, 10, HandType.HARD, isPair = true) to StrategyAction.STAND, // Stand vs 10
    StrategyKey(18, 11, HandType.HARD, isPair = true) to StrategyAction.STAND, // Stand vs Ace

    // Pair of 10s (10, J, Q, K - Total 20, Hard) - Always Stand
    *(2..11).map { dealer ->
        StrategyKey(
            20,
            dealer,
            HandType.HARD,
            isPair = true
        ) to StrategyAction.STAND
    }.toTypedArray()

).flatMap {
    // This complex flatMap structure is to allow the use of `*` (spread operator)
    // with `mapOf()` which expects `Pair` varargs.
    // If it's a single Pair, it becomes a list of one Pair.
    // If it's an Array<Pair> (from `toTypedArray()`), it's spread into individual Pairs.
    if (it is Pair<*, *>) {
        listOf(it as Pair<StrategyKey, StrategyAction>)
    } else {
        (it as Array<Pair<StrategyKey, StrategyAction>>).toList()
    }
}.toMap()


// Helper function to get card value for strategy chart (Ace = 11, Face = 10)
fun getStrategyCardValue(card: String): Int {
    return when (card.uppercase()) {
        "A" -> 11
        "K", "Q", "J" -> 10
        else -> card.toIntOrNull() ?: 0 // Handle potential errors if card isn't a number
    }
}

