package indigo

object Deck {
    private val deck = mutableListOf<Card>()

    init {
        createDeck()
    }

    private fun createDeck() {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                deck.add(Card(rank, suit))
            }
        }
    }

    fun resetDeck() {
        deck.clear()
        createDeck()
    }

    fun shuffleDeck() {
        deck.shuffle()
    }

    fun drawCards(numberOfCards: Int) {
        if (numberOfCards in 1..deck.size) {
            repeat(numberOfCards) {
                print("${deck.last()} ")
                deck.remove(deck.last())
            }
        } else {
            println("The remaining cards are insufficient to meet the request.")
        }
    }

    fun drawCard() {
        // TODO("Draw a specific card from the Deck")
    }

    fun printDeck() {
        for (card in deck) {
            print("$card ")
        }
    }

    fun dealCards(hand: Hand, numberOfCards: Int) {
        repeat(numberOfCards) {
            hand.addCard(deck.last())
            deck.remove(deck.last())
        }
    }

    fun getSize(): Int {
        return deck.size
    }
}