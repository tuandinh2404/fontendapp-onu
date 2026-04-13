import java.io.StringReader
import java.util.Properties
import com.android.build.api.variant.BuildConfigField


plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.network"
    compileSdk = 36


    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}

val backendUrl: Provider<String> =  providers
    .fileContents(rootProject.layout.projectDirectory.file("local.properties"))
    .asText
    .map { text ->
    val properties = Properties().apply { load(StringReader(text)) }
    properties.getProperty("BACKEND_URL", "http://example.com")
}
    .orElse("http://example.com")

androidComponents {
    onVariants { variant ->
        variant.buildConfigFields?.put(
            "BACKEND_URL",
            backendUrl.map { value ->
                BuildConfigField(
                    type = "String",
                    value = "\"$value\"",
                    comment = null
                )
            }
        )
    }
}