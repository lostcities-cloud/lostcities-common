import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.7.+"
}


group = "io.dereknelson.lostcities-cloud"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

val ktlint by configurations.creating

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.30-RC")
    implementation("org.springframework.data:spring-data-jpa:2.5.4")
    implementation("org.springframework.data:spring-data-commons:2.5.4")
    implementation("org.springframework.security:spring-security-config:5.5.1")
    implementation("org.springframework.security:spring-security-web:5.5.1")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.springframework.security:spring-security-core:5.5.1")
    implementation("javax.persistence:javax.persistence-api:2.2")

    compileOnly("org.springframework:spring-web:5.3.+")
    compileOnly("javax.servlet:servlet-api:3.0-alpha-1")
    compileOnly("org.slf4j:slf4j-api:1.7.+")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

    ktlint("com.pinterest:ktlint:0.44.0") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val outputDir = "${project.layout.buildDirectory}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
}

tasks.withType<KotlinCompile>() {

    kotlinOptions {
        jvmTarget = "17"
        apiVersion = "1.7"
        languageVersion = "1.7"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }

    // you can also add additional compiler args,
    // like opting in to experimental features
    kotlinOptions.freeCompilerArgs += listOf(
        "-opt-in=kotlin.RequiresOptIn",
    )
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lostcities-cloud/lostcities-models")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allSource)
}
