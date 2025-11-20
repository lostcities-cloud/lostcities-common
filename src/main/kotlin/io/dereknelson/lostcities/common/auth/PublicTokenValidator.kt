package io.dereknelson.lostcities.common.auth

import io.dereknelson.lostcities.common.auth.entity.UserRef
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.security.SignatureException
import java.util.*
import java.util.stream.Collectors

@Component @Lazy
class PublicTokenValidator() {
    val publicKeyService: PublicKeyService =  PublicKeyService()
    var publicKey: PublicKey? = null

    private val log = LoggerFactory.getLogger(PublicTokenValidator::class.java)
    private var tokenValidityInMilliseconds: Long = 60
    private var tokenValidityInMillisecondsForRememberMe: Long = 60 * 60

    // @Value("application.authentication.jwt.token-validity-in-seconds")
    private var tokenValidityInSeconds: String = (60 * 60 * 24).toString()

    // @Value("application.security.authentication.jwt.token-validity-in-seconds-for-remember-me")
    private var tokenValidityInSecondsForRememberMe: String = (60 * 60 * 24 * 7).toString()

    init {
        tokenValidityInMilliseconds = 1000 * tokenValidityInSeconds.toLong()
        tokenValidityInMillisecondsForRememberMe =
            1000 * tokenValidityInSecondsForRememberMe.toLong()
    }

    fun getAuthentication(token: String?): LostCitiesAuthenticationToken? {
        if(publicKey == null) {
            publicKey = publicKeyService.getPublicKey()
        }

        try {

            val jwtsParser = Jwts.parser()
                .verifyWith(publicKey!!)
                .build()

            val claims = jwtsParser
                .parseSignedClaims(token).payload
            val authorities: MutableCollection<GrantedAuthority> = Arrays.stream(
                claims[AUTHORITIES_KEY].toString().split(",".toRegex()).toTypedArray(),
            )
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
            val principal = UserRef(claims[USER_ID_KEY].toString().toLong(), claims[LOGIN_KEY].toString(), claims[EMAIL_KEY].toString())

            val details = principal.asUserDetails(token!!, authorities)

            details.isAuthenticated = true

            return LostCitiesAuthenticationToken(principal, details, token, authorities)
        } catch (e: JwtException) {
            return null
        }
    }

    fun validateToken(authToken: String?): Boolean {
        if(publicKey == null) {
            publicKey = publicKeyService.getPublicKey()
        }

        try {
            parseToken(authToken!!, publicKey!!)
            return true
        } catch (_: Exception) {
            return false
        }
    }

    fun parseToken(token: String, publicKey: PublicKey): Jws<Claims>? {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)


        } catch (e: SignatureException) {
            log.info("Invalid JWT signature.")
            log.trace("Invalid JWT signature trace: {}", e)
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT token.")
            log.trace("Invalid JWT token trace: {}", e)
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token.")
            log.trace("Expired JWT token trace: {}", e)
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token.")
            log.trace("Unsupported JWT token trace: {}", e)
        } catch (e: IllegalArgumentException) {
            log.info("JWT token compact of handler are invalid.")
            log.trace("JWT token compact of handler are invalid trace: {}", e)
        }
        return null
    }

    private fun UserRef.asUserDetails(token: String, authorities: Collection<GrantedAuthority>): LostCitiesUserDetails {
        return LostCitiesUserDetails(
            id!!,
            login!!,
            email!!,
            userRef = this,
            token = token,
            authority = authorities.toSet(),
            accountNonLocked = true,
            accountNonExpired = true,
            credentialsNonExpired = true,
            enabled = true,
        )
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val USER_ID_KEY = "user_id"
        private const val LOGIN_KEY = "login"
        private const val EMAIL_KEY = "email"
    }
}
