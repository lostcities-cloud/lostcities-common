package io.dereknelson.lostcities.common

/**
 * Application constants.
 */
object Constants {
    // Regex for acceptable logins
    const val LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$"

    const val DEFAULT_LANGUAGE = "en_US"

    const val SYSTEM_ACCOUNT = "system"
    const val ANONYMOUS_ACCOUNT = "anonymous"

    const val ROLE_USER = "user"
    const val ROLE_ADMIN = "admin"
    const val ROLE_READ_ADMIN = "read-admin"

    val AI_USER_NAMES: List<String> = listOf(
        "santiago_woodward",
        "colby_bullock",
        "creed",
        "adolescent-tapeworm",
        "adolescent",
        "toad_defense",
        "karen",
    )
}
