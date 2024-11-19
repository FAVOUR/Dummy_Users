plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.spotless)
}

android {
    namespace = "com.example.core"
    compileSdk = 35

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
    finalizedBy("spotlessApply")
}


defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

       buildConfigField("String", "BASE_URL", "\"https://dummyapi.online/\"")
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(project(":data"))

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

//    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

//    implementation(project(":common"))

   // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson.converter.factory)
    implementation(libs.okhttp.logging.interceptor)


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}