package indigo

data class Card(val rank: Rank, val suit: Suit) {
    override fun toString(): String {
        return "$rank$suit"
    }

    enum class Rank(private val symbol: String) {
        ACE("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K");

        override fun toString(): String {
            return symbol
        }
    }

    enum class Suit(private val symbol: Char) {
        DIAMONDS('\u2666'),
        CLUBS('\u2663'),
        HEARTS('\u2665'),
        SPADES('\u2660');

        override fun toString(): String {
            return symbol.toString()
        }
    }
}