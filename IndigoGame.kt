package indigo

import kotlin.system.exitProcess

class IndigoGame {
    private val table = Hand()
    private val player = Player("Player")
    private val computer = Player("Computer")
    private lateinit var currentPlayer: Player
    private lateinit var wonLast: Player
    private lateinit var firstPlayed: Player
    private val pointsCards = listOf(Card.Rank.TEN, Card.Rank.JACK, Card.Rank.QUEEN, Card.Rank.KING, Card.Rank.ACE)
    val strategy = Strategy()

    fun startGame() {
        initiate()

        while (true) {
            if (Deck.getSize() > 0 && player.getSize() == 0 && computer.getSize() == 0) {
                Deck.dealCards(player, 6)
                Deck.dealCards(computer, 6)
            } else if (Deck.getSize() == 0 && player.getSize() == 0 && computer.getSize() == 0) {
                endGame()
            } else {
                takeTurn()
            }
        }
    }

    private fun initiate() {
        Deck.resetDeck()
        Deck.shuffleDeck()

        println("Indigo Card Game")

        currentPlayer = setPlayer()
        wonLast = currentPlayer //In the rare case where none of the players win any cards, then all cards go to the player who played first.
        firstPlayed = currentPlayer //If the players have the same number of cards, then the player who played first gets these points.

        Deck.dealCards(table, 4)
        Deck.dealCards(player, 6)
        Deck.dealCards(computer, 6)

        print("Initial cards on the table: ")
        table.printCards()
    }

    private fun takeTurn() {
        printTable()

        if (currentPlayer == player) {
            playSelectedCard()
            currentPlayer = computer
        } else {
            playSelectedCard()
            currentPlayer = player
        }
    }

    private fun printTable() {
        println()
        if (table.getSize() == 0) {
            println("No cards on the table")
        } else {
            println("${table.getSize()} cards on the table, and the top card is ${table.getTop()}")
        }
    }

    private fun checkWin(card: Card, player: Player) {
        if (table.getSize() <= 1) return
        if (card.suit == table.getPenultimate().suit || card.rank == table.getPenultimate().rank) {
            for (c in table.cards) {
                player.pile.addCard(c)
            }
            table.cards.clear()
            wonLast = player

            println("${player.playerName} wins cards")
            player.score = calculateScore(this.player)
            computer.score = calculateScore(this.computer)
            printScore()
        }
    }

    private fun printScore() {
        println("Score: Player ${player.score} - Computer ${computer.score}")
        println("Cards: Player ${player.pile.getSize()} - Computer ${computer.pile.getSize()}")
    }

    private fun calculateScore(player: Player): Int {
        var score = 0
        for (card in player.pile.cards) {
            if (pointsCards.contains(card.rank)) score++
        }
        return score
    }

    private fun playerSelectCard(): Card {
        println("Choose a card to play (1-${player.getSize()}):")
        val input = readln()
        var selected = 0
        if (input == "exit") {
            exit()
        } else if (input.toIntOrNull() in 1..player.getSize()) {
            selected = input.toInt()
        } else {
            return playerSelectCard()
        }
        return player.getCard(selected)
    }

    private fun playSelectedCard() {
        val selectedCard: Card

        if (currentPlayer == player) {
            print("Cards in hand: ")
            player.printHand()
            selectedCard = playerSelectCard()
        } else {
            computer.printCards()
            selectedCard = if (table.getSize() != 0) strategy.selectCard(computer, table.getTop()) else strategy.selectCard(computer)
            println("${computer.playerName} plays $selectedCard")
        }
        table.addCard(selectedCard)
        currentPlayer.removeCard(selectedCard)
        checkWin(selectedCard, currentPlayer)
    }

    private fun setPlayer(): Player {
        println("Play first?")
        return when (readln().uppercase()) {
            "YES" -> player
            "NO" -> computer
            "EXIT" -> exitProcess(0)
            else -> setPlayer()
        }
    }

    private fun endGame() {
        printTable()

        for (c in table.cards) {
            wonLast.pile.addCard(c)
        }
        table.cards.clear()

        player.score = calculateScore(this.player)
        computer.score = calculateScore(this.computer)

        if (player.pile.getSize() > computer.pile.getSize()) {
            player.score += 3
        } else if (player.pile.getSize() < computer.pile.getSize()) {
            computer.score += 3
        } else {
            firstPlayed.score += 3
        }

        printScore()
        exit()
    }

    private fun exit() {
        println("Game Over")
        exitProcess(0)
    }
}