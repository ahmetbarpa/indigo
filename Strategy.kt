package indigo

class Strategy {
    fun identifyCandidates(computer: Player, lastCard: Card): MutableSet<Card> {
        val candidateCards = mutableSetOf<Card>()

        for (card in computer.cards) {
            if (card.suit == lastCard.suit || card.rank == lastCard.rank) {
                candidateCards.add(card)
            }
        }

        return candidateCards
    }

    fun selectOneCandidate(candidates: MutableSet<Card>, lastCard: Card): Card {
        val possibleCards = mutableSetOf<Card>()

        //println("selectOneCandidate: $candidates, $lastCard")

        for (card in candidates) {
            if (card.suit == lastCard.suit) {
                possibleCards.add(card)
            }
        }

        if (possibleCards.size >= 2) return possibleCards.random()
        else if (possibleCards.isEmpty()) {
            for (card in candidates) {
                if (card.rank == lastCard.rank) {
                    possibleCards.add(card)
                }
            }
            return possibleCards.random()
        } else {
            possibleCards.removeAll(possibleCards)
            for (card in candidates) {
                if (card.rank == lastCard.rank) {
                    possibleCards.add(card)
                }
            }
            return possibleCards.random()
        }

        //println(possibleCards)
    }

    fun selectCard(computer: Player, lastCard: Card? = null): Card {
        var selected: Card = computer.getTop()
        var candidates = mutableSetOf<Card>()

        if (lastCard != null) candidates = identifyCandidates(computer, lastCard)

        if (computer.getSize() == 1) {
            //println("Size: 1")
            selected = computer.getTop()
        } else if (candidates.size == 1) {
            //println("Candidate: 1")
            selected = candidates.last()
        } else if (lastCard == null) {
            //println("No last Card")
            selected = selectRandomCard(computer)
        } else if (candidates.isEmpty()) {
            //println("Candidates empty")
            selected = selectRandomCard(computer)
        } else {
            //println("else")
            selected = selectOneCandidate(candidates, lastCard)
        }
        return selected
    }

    // In the case of no candidate cards
    fun selectRandomCard(computer: Player): Card {
        val possibleCards = mutableSetOf<Card>()

        for (card in computer.cards) {
            for (other in computer.cards) {
                if (card != other && card.suit == other.suit) {
                    possibleCards.add(card)
                    possibleCards.add(other)
                }
            }
        }

        if (possibleCards.isNotEmpty()) {
            return possibleCards.random()
        } else {
            for (card in computer.cards) {
                for (other in computer.cards) {
                    if (card != other && card.rank == other.rank) {
                        possibleCards.add(card)
                        possibleCards.add(other)
                    }
                }
            }
        }

        return if (possibleCards.isEmpty()) {
            computer.cards.random()
        } else {
            possibleCards.random()
        }
    }
}