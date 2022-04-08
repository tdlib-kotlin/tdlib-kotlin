plugins {
    template.multiplatform.android
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
            path("Android.mk")
        }
    }
}
