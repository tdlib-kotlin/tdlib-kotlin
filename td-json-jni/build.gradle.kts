plugins {
    template.android.library
    template.multiplatform.empty
}

kotlin {
    configureAndroid()
    configureJvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.tdJson)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(projects.tdJsonJniJvmLinuxX64)
                api(projects.tdJsonJniJvmMacosX64)
                api(projects.tdJsonJniJvmWindowsX64)
            }
        }
    }
}

android {
    ndkVersion = "24.0.8215888"
    defaultConfig {
        externalNativeBuild {
            ndkBuild {
                abiFilters("arm64-v8a", "x86", "x86_64")
            }
        }
    }
    externalNativeBuild {
        ndkBuild {
            path("android/main/ndk/Android.mk")
        }
    }
}
