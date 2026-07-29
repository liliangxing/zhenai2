// 根项目构建文件
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.24" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.24" apply false
}

// 所有子模块公共配置
subprojects {
    // ARouter 编译器需要 AROUTER_MODULE_NAME 参数,统一注入到所有 Android 模块
    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            defaultConfig {
                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
                    }
                }
            }
        }
    }
    plugins.withId("com.android.application") {
        extensions.configure<com.android.build.gradle.AppExtension>("android") {
            defaultConfig {
                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
                    }
                }
            }
        }
    }
}
