package io.dereknelson.lostcities.common.auth;

import io.dereknelson.lostcities.common.auth.entity.UserRef
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User
import io.dereknelson.lostcities.common.model.User as CommonUser

class LostCitiesUserDetails (
    val id: Long,
    val login: String,
    val email: String,
    val password: String,
    val userRef: UserRef,
    val token: String,
    val authority: Set<GrantedAuthority>,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean
) : Authentication {

    private var authenticated: Boolean = false;

    override fun getName() = login

    override fun getAuthorities() = authority

    override fun getCredentials(): Any = password

    override fun getDetails(): Any = this

    override fun getPrincipal(): Any = userRef

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }
}
