package io.dereknelson.lostcities.common.auth

import io.dereknelson.lostcities.common.auth.entity.UserRef
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

open class LostCitiesUserDetails(
    private val id: Long,
    val login: String,
    val email: String,
    val userRef: UserRef,
    val token: String,
    val authority: Set<GrantedAuthority>,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
) : Authentication {

    private var authenticated: Boolean = false

    fun getId(): Long {
        return id
    }

    override fun getName(): String {
        return login
    }

    override fun getAuthorities() = authority

    override fun getCredentials(): String = token

    override fun getDetails(): Any = this

    override fun getPrincipal(): Any = userRef

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }
}
