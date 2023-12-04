plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")

}

android {
    namespace = "com.maninmiddle.tests"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.maninmiddle.tests"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // viewModel && liveData && lifecycle
    val currentLifecycleVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$currentLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$currentLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$currentLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // dagger && hilt
    kapt("com.google.dagger:dagger-compiler:2.48.1")
    kapt("com.google.dagger:hilt-compiler:2.48.1")
    implementation("com.google.dagger:dagger:2.48.1")
    implementation("com.google.dagger:hilt-android:2.48.1")


    // core
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}