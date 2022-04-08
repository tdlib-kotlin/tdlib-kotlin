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

tasks.named<org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest>("jvmTest") {
    systemProperty("java.library.path", "/home/oleg/IdeaProjects/learn/td/built/tdlib/linux/lib")
//    systemProperty("java.library.path", "/home/linuxbrew/.linuxbrew/lib")
}
