package io.dereknelson.lostcities.common.auth.entity

import javax.persistence.*

@Embeddable
open class UserRef(
    var id: Long? = null,

    @Transient
    var login: String? = null,

    @Transient
    var email: String? = null
)