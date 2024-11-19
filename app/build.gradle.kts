plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.spotless)
}

android {
    namespace = "com.example.dummyusers"
    compileSdk = 35

    /**
     * Lint
     */
    lint {
        abortOnError = true
        checkReleaseBuilds = true
        xmlReport = true
        htmlReport = true
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

        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }

    tasks.named("build") {
        finalizedBy("spotlessCheck")
    }


    defaultConfig {
        applicationId = "com.example.dummyusers"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}