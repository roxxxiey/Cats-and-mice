plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Уберите эту строку:
    // kotlin("kapt") version "2.0.21"
    // Вместо этого добавьте:
    kotlin("kapt") // без указания версии
}

android {
    namespace = "com.example.netology"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.netology"
        minSdk = 31
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    configurations { implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations")) }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.6.1") // Для Kotlin используйте kapt
    implementation("androidx.room:room-ktx:2.5.0") // Для корутин
}
