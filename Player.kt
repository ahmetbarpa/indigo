package indigo

class Player(val playerName: String, val hasPile: Boolean = false) : Hand() {
    val pile = Hand()
    var score = 0

    fun printHand() {
        var i = 1
        for (card in cards) {
            print("$i)$card ")
            i++
        }
        println()
    }
}