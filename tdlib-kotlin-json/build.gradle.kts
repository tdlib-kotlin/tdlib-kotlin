import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    linuxX64 {
        val main by compilations.getting {
            val tdlib by cinterops.creating {
                defFile(project.file("src/linuxX64Main/interop/tdlib.def"))
            }
        }
    }
}

tasks.named<KotlinNativeTest>("linuxX64Test") {
    environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/lib")
}
