plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.spotless)
}


/**
 * Spotless
 */
spotless {
    kotlin {
        target("src/**/*.kt")

        ktlint("0.44.0").userData(
            mapOf("android" to "true")
        )
    }
    java {
        target("src/**/*.java")
        googleJavaFormat()
    }

    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
    }

}

tasks.named("build") {
    finalizedBy("spotlessCheck")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }

    dependencies {
        implementation(libs.coroutine.core)
    }
}