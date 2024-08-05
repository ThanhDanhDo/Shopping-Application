plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.shopping_application"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shopping_application"
        minSdk = 24
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //lifecycle
//Thư viện này bao gồm các thành phần lifecycle cơ bản như ViewModel và LiveData.
//Các thành phần này giúp quản lý vòng đời của các thành phần giao diện người dùng
// (UI) như Activity và Fragment, giúp đảm bảo dữ liệu và trạng thái của ứng dụng
// được duy trì đúng cách khi vòng đời của UI thay đổi (ví dụ: khi xoay màn hình).
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    //ViewModel
    implementation("androidx.activity:activity-ktx:1.9.1")
    // Thư viện Glide cho tải và hiển thị hình ảnh
    implementation("com.github.bumptech.glide:glide:4.16.0'")
    // Thư viện DotsIndicator để tạo các dấu chấm chỉ thị trang
    implementation("com.tbuonomo:dotsindicator:5.0")
}