import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    id("com.github.rising3.semver") version "0.8.2"
    kotlin("jvm") version "2.0.+"
}


group = "io.dereknelson.lostcities-cloud"
version = project.property("version")!!

repositories {
    mavenCentral()
}

val ktlint by configurations.creating

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.+")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.+")
    implementation("org.springframework.data:spring-data-jpa:3.2.+")
    implementation("org.springframework.data:spring-data-commons:3.2.+")
    implementation("org.springframework.security:spring-security-config:6.1.+")
    implementation("org.springframework.security:spring-security-web:6.1.+")
    implementation("org.springframework.security:spring-security-core:6.1.+")
    compileOnly("org.springframework:spring-web:6.1.+")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.jsonwebtoken:jjwt-api:0.12.7")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.7")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.7")

    compileOnly("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")


    compileOnly("org.slf4j:slf4j-api:1.7.+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

    ktlint("com.pinterest:ktlint:0.49.1") {
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

    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)

        freeCompilerArgs.addAll(listOf(
            "-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn"
        ))
    }
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
            url = uri("https://maven.pkg.github.com/lostcities-cloud/lostcities-common")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            version = project.property("version")!! as String
            from(components["java"])
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allSource)
}
