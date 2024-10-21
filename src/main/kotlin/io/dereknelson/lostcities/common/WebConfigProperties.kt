package io.dereknelson.lostcities.common
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "web")
data class WebConfigProperties @ConstructorBinding constructor(val cors: Cors) {
    class Cors(
        val allowedOrigins: Array<String>,
        val allowedMethods: Array<String>,
        val maxAge: Long,
        val allowedHeaders: Array<String>,
        val exposedHeaders: Array<String>,
    )
}
