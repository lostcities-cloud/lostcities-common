package io.dereknelson.lostcities.common.events

import io.dereknelson.lostcities.common.model.game.Command
import io.dereknelson.lostcities.common.model.match.Match

class MatchCommandEvent (
    val matchId: Long,
    val match: Match,
    val command: Command
)