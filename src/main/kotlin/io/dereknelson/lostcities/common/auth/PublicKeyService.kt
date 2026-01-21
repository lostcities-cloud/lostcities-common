package io.dereknelson.lostcities.common.auth

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

class PublicKeyService(
    val protocol: String = "http",
    val hostname: String = "localhost",
    val port: Int = 8080,
) {

    var path: String = "/api/accounts/public-key"



    fun getPublicKey(): PublicKey {
        val restClient = RestClient.builder()
            .requestFactory(HttpComponentsClientHttpRequestFactory())

            .baseUrl(getBaseUrl())
            .build()

        val dto = restClient.get().uri(path).retrieve()
            .body(PublicKeyDto::class.java)!!

        val pubKeySpec = X509EncodedKeySpec(dto.getPublicKey())
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(pubKeySpec)
    }

    private fun getBaseUrl(): String  {
        return "${protocol}://${hostname}:${port}"
    }
}

