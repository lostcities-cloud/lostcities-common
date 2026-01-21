package io.dereknelson.lostcities.common.model

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    USER,
    ADMIN,
    ANONYMOUS,
    ;
    companion object {
        fun findRoleByName(name: String): Role {
            return Role.entries.first { it.name.equals(name, true) }
        }
    }

    override fun getAuthority(): String {
        return this.toString()
    }
}
