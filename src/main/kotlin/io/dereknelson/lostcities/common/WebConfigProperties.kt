package io.dereknelson.lostcities.common
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "web")
data class WebConfigProperties @ConstructorBinding constructor(val cors: Cors) {
    data class Cors(
        val allowedOrigins: Array<String> = emptyArray(),
        val allowedMethods: Array<String> = emptyArray(),
        val maxAge: Long = 0,
        val allowedHeaders: Array<String> = emptyArray(),
        val exposedHeaders: Array<String> = emptyArray(),
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Cors

            if (maxAge != other.maxAge) return false
            if (!allowedOrigins.contentEquals(other.allowedOrigins)) return false
            if (!allowedMethods.contentEquals(other.allowedMethods)) return false
            if (!allowedHeaders.contentEquals(other.allowedHeaders)) return false
            if (!exposedHeaders.contentEquals(other.exposedHeaders)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = maxAge.hashCode()
            result = 31 * result + allowedOrigins.contentHashCode()
            result = 31 * result + allowedMethods.contentHashCode()
            result = 31 * result + allowedHeaders.contentHashCode()
            result = 31 * result + exposedHeaders.contentHashCode()
            return result
        }
    }
}
