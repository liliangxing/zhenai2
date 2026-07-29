// 公共库: base 类、工具、路由、常量、扩展
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.zhenai2.common"
    compileSdk = 34
    defaultConfig { minSdk = 23 }
    buildFeatures { buildConfig = true; viewBinding = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    api("androidx.core:core-ktx:1.13.1")
    api("androidx.appcompat:appcompat:1.7.0")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    api("androidx.activity:activity-ktx:1.9.0")
    api("androidx.fragment:fragment-ktx:1.8.0")
    api("com.google.android.material:material:1.12.0")

    // ARouter(原 App 使用 ARouter 路由,复刻保留)
    api("com.alibaba:arouter-api:1.5.2")

    // Gson(原 App 数据序列化)
    api("com.google.code.gson:gson:2.10.1")

    // 日志
    api("com.orhanobut:logger:2.2.0")
}
