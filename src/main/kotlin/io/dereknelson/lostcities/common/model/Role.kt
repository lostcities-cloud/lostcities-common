package io.dereknelson.lostcities.common.model

import io.dereknelson.lostcities.common.RoleConstants
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority

enum class Role(


    @Id
    @Column(name = "authority")
    private val authority: String,
) : GrantedAuthority {
    USER(RoleConstants.USER),
    ADMIN(RoleConstants.ADMIN),
    ANONYMOUS(RoleConstants.ANONYMOUS),
    ;
    companion object {
        const val USER = "USER"
        const val ADMIN = "ADMIN"
        const val ANONYMOUS = "ANONYMOUS"

        fun findRoleByName(name: String): Role {
            return Role.entries.first { it.name.equals(name, true) }
        }
    }

    override fun getAuthority(): String {
        return this.authority
    }

}
