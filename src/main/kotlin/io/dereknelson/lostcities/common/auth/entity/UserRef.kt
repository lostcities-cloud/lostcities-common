package io.dereknelson.lostcities.common.auth.entity

import jakarta.persistence.Embeddable

@Embeddable
open class UserRef(
    var id: Long? = null,

    @Transient
    var login: String? = null,

    @Transient
    var email: String? = null,
)
