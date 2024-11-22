plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.spotless)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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
        finalizedBy("spotlessApply")
    }

    /**
     * Jacoco
     */

    tasks.register<JacocoReport>("jacocoTestReport") {
        group = "Verification"
        description = "Generates a JaCoCo code coverage report for the debug unit tests."


        dependsOn("testDebugUnitTest")

        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
        }

        val fileFilter = listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*"
        )

        val debugTree = fileTree("${getLayout().buildDirectory}/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }
        val mainSrc = "src/main/java"

        sourceDirectories.setFrom(files(mainSrc))
        classDirectories.setFrom(files(debugTree))
        executionData.setFrom(fileTree(getLayout().buildDirectory).include("jacoco/testDebugUnitTest.exec"))
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.dummyusers"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://dummyapi.online/\"")


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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //Network
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson.converter.factory)


    // Dependencies for local unit tests
//    testImplementation(composeBom)
    testImplementation(libs.junit)
//    testImplementation(libs.junit4)
//    testImplementation(libs.androidx.archcore.testing)
    testImplementation(libs.kotlinx.coroutines.test)
//    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.google.truth)



    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}