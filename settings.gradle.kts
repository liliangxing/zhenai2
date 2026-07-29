// 珍爱2 - 复刻项目模块声明
// 新包名: com.zhenai2.android (原 com.zhenai.android)

pluginManagement {
    repositories {
        // curl 实测直连 Google Maven 稳定(20MB/s),国内镜像反而在沙箱内 SYN 挂起
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.tencentcloud.com/repository/maven/") }
    }
}

rootProject.name = "zhenai2"

include(":app")
include(":lib-common")
include(":lib-network")
include(":module-login")
include(":module-home")
include(":module-mine")
include(":module-live")
include(":module-chat")
include(":module-cert")
include(":module-moment")
include(":module-pay")
include(":module-emotion")
include(":module-web")
