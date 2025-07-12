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
// In core/BlackjackStrategy.kt

// ... (HandType, StrategyAction, StrategyKey definitions)

val basicStrategyChart: Map<StrategyKey, StrategyAction> = mapOf(
    // --- Hard Totals (Non-Pairs) ---
    // isPair defaults to false, or you can be explicit: isPair = false
    StrategyKey(5, 2, HandType.HARD) to StrategyAction.HIT, // Implicitly isPair = false
    StrategyKey(8, 5, HandType.HARD, isPair = false) to StrategyAction.HIT, // Explicitly isPair = false

    // Player Total 9 (Non-Pair)
    StrategyKey(9, 3, HandType.HARD) to StrategyAction.DOUBLE_DOWN, // isPair = false (default)

    // --- Soft Totals (Non-Pairs) ---
    // isPair defaults to false
    StrategyKey(13, 5, HandType.SOFT) to StrategyAction.DOUBLE_DOWN,

    // --- Pairs (Splitting Strategy) ---
    // **For these, playerTotal is the sum of the two cards.**

    // Pair of 2s (Player Total 4, Hard)
    // Dealer shows 2-7: SPLIT
    StrategyKey(4, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(4, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    // Dealer shows 8+: HIT
    StrategyKey(4, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 9, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 10, HandType.HARD, isPair = true) to StrategyAction.HIT,
    StrategyKey(4, 11, HandType.HARD, isPair = true) to StrategyAction.HIT,

    // Pair of 3s (Player Total 6, Hard)
    // Similar logic: SPLIT vs dealer 2-7, HIT vs 8+
    StrategyKey(6, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    // ... more 3s
    StrategyKey(6, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(6, 8, HandType.HARD, isPair = true) to StrategyAction.HIT,
    // ... more 3s

    // Pair of 8s (Player Total 16, Hard) - Always SPLIT
    StrategyKey(16, 2, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 3, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 4, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 5, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 6, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 7, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 8, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 9, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 10, HandType.HARD, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(16, 11, HandType.HARD, isPair = true) to StrategyAction.SPLIT,

    // Pair of Aces (Player Total can be 2 (Hard) or 12 (Soft) - Always SPLIT)
    // Let's use playerTotal = 12, handType = SOFT for Aces, as they are initially soft.
    // Or you could represent A,A as playerTotal = 2, handType = HARD, isPair = true.
    // Consistency is key. Let's use Soft 12 for (A,A)
    StrategyKey(12, 2, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 3, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 4, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 5, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 6, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 7, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 8, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 9, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 10, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,
    StrategyKey(12, 11, HandType.SOFT, isPair = true) to StrategyAction.SPLIT,

    // What about a hand like Player 16 (10, 6) vs Dealer 10? This is STAND.
    StrategyKey(16, 10, HandType.HARD, isPair = false) to StrategyAction.STAND,

    // What about a hand like Player 16 (8, 8) vs Dealer 10? This is SPLIT.
    // This is now distinct from the (10,6) example above because isPair = true.
    // Already defined above for Pair of 8s.

    // You MUST continue to populate this map comprehensively.
)


// Helper function to get card value for strategy chart (Ace = 11, Face = 10)
fun getStrategyCardValue(card: String): Int {
    return when (card.uppercase()) {
        "A" -> 11
        "K", "Q", "J" -> 10
        else -> card.toIntOrNull() ?: 0 // Handle potential errors if card isn't a number
    }
}

