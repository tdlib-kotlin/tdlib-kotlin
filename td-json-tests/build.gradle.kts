plugins {
    template.android.library
    template.multiplatform.empty
}

kotlin {
    configureAndroid()
    configureJvm()
    configureNative()

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(projects.tdJsonPrebuilt)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation("androidx.test:runner:1.4.0")
            }
        }
    }
}
