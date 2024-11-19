// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.sonarqube") version "5.1.0.4882"
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
}

sonar {
    properties {
        property ("sonar.projectKey", "FAVOUR_Dummy_Users")
        property ("sonar.organization", "favour")
        property ("sonar.host.url", "https://sonarcloud.io")
    }
}
