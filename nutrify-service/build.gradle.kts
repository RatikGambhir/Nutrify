plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.nutrify"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

kotlin {
    jvmToolchain(23)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions { jvmTarget = "23" }
}

dependencies {
    // --- Ktor server ---
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.sse)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.config.yaml)

    // --- DB / pool / logging ---
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation(libs.hikariCP)          // ← correct accessor (not libs.HikariCP)
    implementation(libs.logback.classic)

    // --- Ktor client (optional in a server module) ---
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.apache)

    // --- Koin (optional; remove if doing manual DI) ---
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    

    // --- Tests ---
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}