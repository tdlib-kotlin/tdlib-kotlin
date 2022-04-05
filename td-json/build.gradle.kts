plugins {
    template.multiplatform.all
}
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.tdJsonApi)
            }
        }
        androidMain {
            dependencies {
                api(projects.tdJsonTdlibAndroid)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        androidTest {
            dependencies {
                implementation("androidx.test:runner:1.4.0")
            }
        }
    }
}
