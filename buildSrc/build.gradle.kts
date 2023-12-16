val kotlin = "1.9.21"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

version = "0.0.1-SNAPSHOT"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        languageVersion = "1.8"
        jvmTarget = "17"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.25.7")
    implementation("com.github.javaparser:javaparser-core:3.25.7")
    implementation("com.github.javaparser:javaparser-core-serialization:3.25.7")
}
