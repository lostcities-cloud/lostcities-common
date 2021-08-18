package io.dereknelson.lostcities.common.model.match

import java.util.*

data class UserPair (
    var user1: String,
    var user2: String?=null,

    var score1: Int=0,
    var score2: Int=0
) {
    val isPopulated: Boolean
        get() = user2 != null

    fun contains(user: String): Boolean {
        return user1 == user || user2 == user
    }

    fun shuffled(random: Random) {
        if(isPopulated) {
            val users = listOf(this.user1, this.user2!!)
                .shuffled(random)

            this.user1 = users[0]
            this.user2 = users[1]
        }
    }
}
