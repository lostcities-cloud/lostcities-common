package io.dereknelson.lostcities.common.model.match

import io.dereknelson.lostcities.common.model.User
import java.time.LocalDateTime
import java.util.*


open class Match (
    val id: Long? = null,
    val players: UserPair,
    val seed: Long,
    val isReady: Boolean = false,
    val isStarted: Boolean = false,
    val isCompleted: Boolean = false,

    val concededBy: Long? = null,

    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
    val createdBy: String? = null,
    var lastModifiedBy: String? = null
) {
    companion object {
        fun buildMatch(player: Long, random: Random): Match{
            return Match(players= UserPair(user1=player), seed=random.nextLong())
        }
    }
}