package io.dereknelson.lostcities.common
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "web")
data class WebConfigProperties(
    val allowedOrigins: List<String> = emptyList(),
    val allowedMethods: List<String> = emptyList(),
    val maxAge: Long = 0,
    val allowedHeaders: List<String> = emptyList(),
    val exposedHeaders: List<String> = emptyList(),
)
