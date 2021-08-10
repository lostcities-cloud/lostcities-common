package io.dereknelson.lostcities.common.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class UserRef(
    @Id
    val id: Long? = null,
    val login: String? = null,
    val email: String? = null
)