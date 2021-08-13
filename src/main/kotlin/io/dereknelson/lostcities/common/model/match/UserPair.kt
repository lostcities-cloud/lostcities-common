package io.dereknelson.lostcities.common.model.match

import io.dereknelson.lostcities.common.model.User
import java.util.*

data class UserPair (
    var user1: Long?=null,
    var user2: Long?=null,

    var score1: Int?=null,
    var score2: Int?=null
) {
    val isPopulated: Boolean
        get() = user1 != null && user2 != null

    fun contains(user: Long): Boolean {
        return user1 == user || user2 == user
    }

    fun shuffled(seed: Long) {
        if(isPopulated) {
            val users: List<Long> = listOf(this.user1!!, this.user2!!)
                .shuffled(Random(seed))

            this.user1 = users[0]
            this.user2 = users[1]
        }
    }
}
