plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.uas_ppapba_syafiqabdillahhabib_514688"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.uas_ppapba_syafiqabdillahhabib_514688"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.androidx.activity.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx.v276)
    implementation(libs.androidx.navigation.ui.ktx.v276)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v270)
    implementation(libs.androidx.lifecycle.livedata.ktx.v270)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx.v253)
    implementation(libs.androidx.navigation.ui.ktx.v253)



    // Room
    implementation(libs.androidx.room.runtime.v261)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.databinding.adapters)
    kapt(libs.androidx.room.compiler)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    // Glide for image loading
    implementation(libs.glide.v4160)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}