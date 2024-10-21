package io.dereknelson.lostcities.common.model

import io.dereknelson.lostcities.common.Constants

data class User(
    val id: Long?,
    val login: String,
    val email: String,
    val langKey: String = Constants.DEFAULT_LANGUAGE,
)
