package indigo

open class Hand {
    val cards = mutableListOf<Card>()

    fun getCard(position: Int): Card {
        return this.cards[position - 1]
    }

    fun removeCard(position: Int) {
        this.cards.removeAt(position - 1)
    }

    fun getAndRemove(position: Int): Card {
        val card = this.cards[position - 1]
        this.cards.removeAt(position - 1)
        return card
    }

    fun getTop(): Card {
        return this.cards.last()
    }

    fun getPenultimate(): Card {
        return this.cards[getSize() - 2]
    }

    fun getSize(): Int {
        return this.cards.size
    }

    fun addCard(card: Card) {
        this.cards.add(card)
    }

    fun removeCard(card: Card) {
        this.cards.remove(card)
    }

    fun printCards() {
        for (card in this.cards) {
            print("$card ")
        }
        println()
    }
}