plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp").version("1.7.10-1.0.6")
}
android {
    compileSdk = 33
    defaultConfig {
        applicationId = "ir.apptune.antispam"
        minSdk = 23
        targetSdk = 33
        versionCode = 605
        versionName = "6.0.5"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "ir.apptune.antispam"
}

dependencies {
    // Kotlin libraries
    implementation("androidx.core:core-ktx:1.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Support libraries
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")

    // ViewModel + LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    ksp("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")

    // Koin for Android
    implementation("io.insert-koin:koin-android:3.3.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
}
