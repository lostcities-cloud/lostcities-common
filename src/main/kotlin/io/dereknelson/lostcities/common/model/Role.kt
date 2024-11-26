package io.dereknelson.lostcities.common.model

import io.dereknelson.lostcities.common.Constants
import jakarta.persistence.Column
import org.springframework.security.core.GrantedAuthority

enum class Role(
    @Column(name = "authority")
    private val authority: String
): GrantedAuthority {

    ROLE_USER(Constants.ROLE_USER),
    ROLE_ADMIN(Constants.ROLE_ADMIN);
    companion object {
        fun findRoleByName(name: String): Role {
            return Role.entries.first { it.name.equals(name, true) }
        }
    }

    override fun getAuthority(): String {
        return this.authority
    }
}
