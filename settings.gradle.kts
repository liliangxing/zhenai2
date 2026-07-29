// 珍爱2 - 复刻项目模块声明
// 新包名: com.zhenai2.android (原 com.zhenai.android)

pluginManagement {
    repositories {
        // 优先使用阿里云镜像(沙箱内稳定),Google/中央仓库作兜底
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
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
