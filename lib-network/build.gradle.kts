// 网络库: Retrofit + OkHttp + 拦截器 + 设备指纹 + API 返回结构
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zhenai2.network"
    compileSdk = 34
    defaultConfig { minSdk = 23 }
    buildFeatures { buildConfig = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    api(project(":lib-common"))

    // Retrofit / OkHttp(原 App 网络栈)
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.11.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // 协程
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}
