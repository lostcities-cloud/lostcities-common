package io.dereknelson.lostcities.common.auth

import io.dereknelson.lostcities.common.auth.entity.UserRef
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion


class LostCitiesAuthenticationToken(
    private val userRef: UserRef,
    private val userDetails: LostCitiesUserDetails,
    private val credentials: String?,
    authorities: MutableCollection<GrantedAuthority>?
) : AbstractAuthenticationToken(authorities) {
    private val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID

    override fun getPrincipal(): Any {
        return userDetails
    }

    override fun getCredentials(): Any {
        return credentials!!
    }

    override fun isAuthenticated(): Boolean {
        return true;
    }
}