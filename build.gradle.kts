plugins {
    id("io.micronaut.application") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.test-resources") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
    id("org.jetbrains.kotlin.kapt") version "2.1.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"

    kotlin("jvm")
}

version = "0.1"
group = "com.msantosfelipe.financehub"

repositories {
    mavenCentral()
}

val kotlinxCoroutinesVersion = "1.10.2"

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client")
    runtimeOnly("org.yaml:snakeyaml")
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$kotlinxCoroutinesVersion")

    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    kapt("io.micronaut:micronaut-inject-java")
}

application {
    mainClass = "com.msantosfelipe.financehub.Application"
}
java {
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.msantosfelipe.financehub.*")
    }
    testResources {
        additionalModules.add("jdbc-postgresql")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

kotlin {
    jvmToolchain(21)
}
