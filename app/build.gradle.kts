// app 主壳模块
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "com.zhenai2.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zhenai2.android"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        // 珍爱网 H5/接口域名,编译期注入 BuildConfig
        buildConfigField("String", "API_HOST", "\"https://api.zhenai.com\"")
        buildConfigField("String", "API_HOST_H5", "\"https://api.zhenai.com\"")
        buildConfigField("String", "FINGER_HOST", "\"https://secdffinger.zhenai.com\"")
        buildConfigField("String", "H5_HOST", "\"https://i.zhenai.com\"")

        // 多 dex(原 App 业务量大,保留 multidex)
        multiDexEnabled = true

        ndk {
            // 原 App 提供 arm64-v8a / armeabi-v7a
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = false
        dataBinding = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    packaging {
        resources.excludes += setOf(
            "META-INF/*.kotlin_module",
            "META-INF/DEPENDENCIES",
            "META-INF/LICENSE*",
            "META-INF/NOTICE*"
        )
    }

    lint { abortOnError = false }
}

dependencies {
    implementation(project(":lib-common"))
    implementation(project(":lib-network"))
    kapt("com.alibaba:arouter-compiler:1.5.2")
    implementation(project(":module-login"))
    implementation(project(":module-home"))
    implementation(project(":module-mine"))
    implementation(project(":module-live"))
    implementation(project(":module-chat"))
    implementation(project(":module-cert"))
    implementation(project(":module-moment"))
    implementation(project(":module-pay"))
    implementation(project(":module-emotion"))
    implementation(project(":module-web"))

    // AndroidX 基础
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.8.0")

    // UI
    implementation("com.google.android.material:material:1.12.0")
    implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")
    implementation("io.github.scwang90:refresh-header-material:2.1.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:ksp:4.16.0")
}
