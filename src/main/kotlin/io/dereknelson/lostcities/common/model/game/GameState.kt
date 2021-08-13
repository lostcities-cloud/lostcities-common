package io.dereknelson.lostcities.common.model.game

import io.dereknelson.lostcities.common.model.game.components.Card
import io.dereknelson.lostcities.common.model.game.components.Color
import io.dereknelson.lostcities.common.model.game.components.Phase
import io.dereknelson.lostcities.common.model.game.components.PlayArea
import io.dereknelson.lostcities.common.model.match.UserPair
import io.dereknelson.lostcities.common.model.User
import kotlin.collections.LinkedHashSet

class GameState(
    val id : Long,
    players : UserPair,
    private val deck : LinkedHashSet<Card>
) {
    var phase = Phase.PLAY_OR_DISCARD
    var currentPlayer : Long = players.user1!!

    private val discard = PlayArea()

    private val playerAreas: Map<Long, PlayArea> = mapOf(
        players.user1!! to PlayArea(),
        players.user2!! to PlayArea()
    )

    private val playerHands: Map<Long, MutableList<Card>> = mapOf(
        players.user1!! to mutableListOf(),
        players.user2!! to mutableListOf()
    )

    fun nextPhase() {
        phase = if(phase == Phase.PLAY_OR_DISCARD) {
            Phase.DRAW
        } else {
            Phase.PLAY_OR_DISCARD
        }
    }

    fun drawCard(player: Long) {
        if(deck.isNotEmpty()) {
            val drawn = deck.first()
            deck.remove(drawn)
            getHand(player).add(drawn)
        }
    }

    fun drawFromDiscard(player : Long, color: Color) {
        if(canDrawFromDiscard(color)) {
            val cards = discard.get(color)
            val drawn = cards.first()
            cards.remove(drawn)
            getHand(player).add(drawn)
        }
    }

    fun playCard(player : Long, card : Card) {
        if(isCardInHand(player, card)) {
            getPlayerArea(player).get(card.color).add(card)
        }
    }

    fun discard(player : Long, card : Card) {
        if(isCardInHand(player, card)) {
            discard.get(card.color).add(card)
        }
    }

    private fun canDrawFromDiscard(color : Color) : Boolean {
        return !discard.isEmpty(color)
    }

    private fun isCardInHand(player : Long, card : Card) : Boolean {
        return getHand(player).contains(card)
    }

    private fun getHand(player : Long) : MutableList<Card> {
        return playerHands[player]!!
    }

    private fun getPlayerArea(player : Long) : PlayArea {
        return playerAreas[player]!!
    }

}
