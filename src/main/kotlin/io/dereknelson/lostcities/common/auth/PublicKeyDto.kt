package io.dereknelson.lostcities.common.auth

import java.util.Base64

class PublicKeyDto(
    private val publicKey: String,
    val format: String,
    val algorithm: String,
) {
    fun getPublicKey(): ByteArray {
        return Base64.getDecoder().decode(publicKey.toByteArray())
    }
}

