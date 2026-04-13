plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.onu"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.onu"
        minSdk = 28
        targetSdk = 36
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
    implementation(projects.core.designsystem)

    implementation(projects.feature.auth.api)
    implementation(projects.feature.auth.impl)
    implementation(projects.feature.moments.api)
    implementation(projects.feature.moments.impl)
    implementation(projects.feature.messenger.api)
    implementation(projects.feature.messenger.impl)
    implementation(projects.feature.foryou.api)
    implementation(projects.feature.foryou.impl)
    implementation(projects.feature.friends.api)
    implementation(projects.feature.friends.impl)
    implementation(projects.feature.profile.api)
    implementation(projects.feature.profile.impl)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.constraint.layout)

    implementation(libs.kotlinx.serialization.core)

    implementation("androidx.paging:paging-compose:3.3.0")

    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation("com.adamglin:phosphor-icon-android:1.0.0")
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")

    implementation("androidx.navigation:navigation-compose:2.9.3")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation ("androidx.camera:camera-core:1.4.2")
    implementation ("androidx.camera:camera-camera2:1.4.2")
    implementation ("androidx.camera:camera-lifecycle:1.4.2")
    implementation ("androidx.camera:camera-view:1.4.2")
    implementation ("androidx.camera:camera-video:1.4.2")
    implementation ("androidx.camera:camera-extensions:1.4.2")
    implementation ("jp.co.cyberagent.android:gpuimage:2.1.0")

    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.coil-kt:coil-video:2.7.0")

    //github library

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}