import androidx.room.gradle.RoomExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    id("kotlin-parcelize")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.reader(Charsets.UTF_8).use { reader ->
        localProperties.load(reader)
    }
} else {
    throw GradleException("local.properties file not found. Please create it in the root directory.")
}

android {
    namespace = "com.me.weather"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.me.weather"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = localProperties.getProperty("apiKey")
            ?: throw GradleException("'apiKey' not found in local.properties. Please add it.")

        buildConfigField(
            "String",
            "API_KEY",
            "\"$apiKey\"",
        )

        val baseUrl = localProperties.getProperty("baseUrl")
            ?: throw GradleException("'baseUrl' not found in local.properties. Please add it.")

        buildConfigField(
            "String",
            "BASE_URL",
            "\"$baseUrl\"",
        )

        val googleMapApiKey = localProperties.getProperty("googleMapApiKey")
            ?: throw GradleException("'googleMapApiKey' not found in local.properties. Please add it.")

        buildConfigField(
            "String",
            "GOOGLE_MAP_API_KEY",
            "\"$googleMapApiKey\"",
        )

        manifestPlaceholders["googleMapApiKey"] = googleMapApiKey
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
        buildConfig = true
    }
    extensions.configure<RoomExtension> {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)
    ksp(libs.room.compiler)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.hilt.android)
    implementation(libs.hilt.core)
    implementation(libs.kotlinx.kotlinx.coroutines.android)
    implementation(libs.kotlinx.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widgets)
    implementation(libs.maps.ktx)
    implementation(libs.maps.utils.ktx)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.retrofit2.converter.gson)
    implementation(libs.squareup.retrofit2.converter.kotlinx.serialization)
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.timber)
    implementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
}
