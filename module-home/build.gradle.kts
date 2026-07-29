// Home 模块
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
}
android {
    namespace = "com.zhenai2.home"
    compileSdk = 34
    defaultConfig { minSdk = 23 }
    buildFeatures { viewBinding = true }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}
dependencies {
    implementation(project(":lib-common"))
    implementation(project(":lib-network"))
    kapt("com.alibaba:arouter-compiler:1.5.2")
}
