package com.example.cardcountingtrainer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cardcountingtrainer.core.HandType
import com.example.cardcountingtrainer.core.StrategyAction
import com.example.cardcountingtrainer.core.StrategyKey
import com.example.cardcountingtrainer.core.basicStrategyChart
import com.example.cardcountingtrainer.model.PlayingCard

// In viewmodel/PracticeViewModel.kt
data class PracticeScreenUiState(
    val dealerCard: PlayingCard? = null,
    val playerCards: List<PlayingCard> = emptyList(),
    val playerSum: Int = 0,
    val handType: HandType = HandType.HARD,
    val isPair: Boolean = false,
    val correctAnswer: StrategyAction? = null,
    val score: Int = 0,
    val totalAttempts: Int = 0,
    val message: String? = null // For feedback like "Correct!" or "Incorrect"
) {
    val accuracy: Float
        get() = if (totalAttempts == 0) 0f else (score.toFloat() / totalAttempts.toFloat()) * 100
}

class PracticeViewModel : ViewModel() {

    var uiState by mutableStateOf(PracticeScreenUiState())
        private set

    private val deck: MutableList<PlayingCard> = mutableListOf()

    init {
        resetDeck()
        generateNewHand()
    }

    private fun resetDeck() {
        deck.clear()
        val suits = listOf("HEARTS", "DIAMONDS", "CLUBS", "SPADES")
        val values = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        for (suit in suits) {
            for (value in values) {
                deck.add(PlayingCard(suit, value))
            }
        }
        deck.shuffle()
    }

    private fun dealCard(): PlayingCard {
        if (deck.isEmpty()) {
            resetDeck() // Reshuffle if deck is empty
        }
        return deck.removeAt(0)
    }

    fun generateNewHand() {
        if (deck.size < 3) resetDeck() // Ensure enough cards

        val dealerVisibleCard = dealCard()
        val playerCard1 = dealCard()
        val playerCard2 = dealCard()
        val currentKPlayerCards = listOf(playerCard1, playerCard2)

        val handDetails = calculateHandDetails(currentKPlayerCards)
        val currentIsPair =
            currentKPlayerCards.size == 2 && playerCard1.getRank() == playerCard2.getRank()

        val strategyKey = StrategyKey(
            playerTotal = handDetails.sum,
            dealerUpCard = dealerVisibleCard.getStrategyValue(),
            handType = handDetails.type,
            isPair = currentIsPair
        )

        uiState = uiState.copy(
            dealerCard = dealerVisibleCard,
            playerCards = currentKPlayerCards,
            playerSum = handDetails.sum,
            handType = handDetails.type,
            isPair = currentIsPair,
            correctAnswer = basicStrategyChart[strategyKey],
            message = null // Clear previous message
        )

        if (uiState.correctAnswer == null) {
            // This should ideally not happen if your chart is complete
            // and card values are handled correctly.
            // Consider logging this or handling it as an error.
            println("Warning: No strategy found for key: $strategyKey")
            // Potentially regenerate or set a default action
        }
    }

    private data class HandCalculationResult(val sum: Int, val type: HandType)

    private fun calculateHandDetails(cards: List<PlayingCard>): HandCalculationResult {
        var sum = 0
        var aces = 0
        cards.forEach { card ->
            val cardValue = card.getStrategyValue()
            if (card.getRank() == "A") {
                aces++
            }
            sum += cardValue
        }

        // Adjust for Aces if sum > 21
        var tempSum = sum
        var tempAces = aces
        while (tempSum > 21 && tempAces > 0) {
            tempSum -= 10 // Change Ace from 11 to 1
            tempAces--
        }

        val handType =
            if (aces > tempAces) HandType.SOFT else HandType.HARD // Soft if an Ace is still 11
        return HandCalculationResult(tempSum, handType)
    }


    fun submitAnswer(playerAction: StrategyAction) {
        if (uiState.correctAnswer == null) {
            // Can't check if there's no correct answer defined (e.g. chart error)
            generateNewHand()
            return
        }

        val isCorrect = playerAction == uiState.correctAnswer
        val newScore = if (isCorrect) uiState.score + 1 else uiState.score
        val newMessage =
            if (isCorrect) "Correct!" else "Incorrect. Correct was: ${uiState.correctAnswer}"


        uiState = uiState.copy(
            score = newScore,
            totalAttempts = uiState.totalAttempts + 1,
            message = newMessage
        )

        // Optionally, automatically generate a new hand after a short delay
        // viewModelScope.launch {
        //     delay(1500) // 1.5 seconds
        //     if (uiState.message == newMessage) { // Only proceed if state hasn't changed again
        //         generateNewHand()
        //    }
        // }
    }
}