import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

fun KotlinMultiplatformExtension.configureAndroid() {
    android {
        publishAllLibraryVariants()
    }
}

fun KotlinMultiplatformExtension.configureJvm() {
    jvm {

    }
}

fun KotlinMultiplatformExtension.configureJs() {
    js {
        browser()
    }
}

fun KotlinMultiplatformExtension.configureNative() {
    linuxX64()
    macosX64()

    sourceSets {
        val nativeMain by creating {
            dependsOn(getByName("commonMain"))
        }

        val nativeTest by creating {
            dependsOn(getByName("commonTest"))
        }

        targets.all {
            if (this !is KotlinNativeTarget) return@all

            getByName("${name}Main").dependsOn(nativeMain)
            getByName("${name}Test").dependsOn(nativeTest)
        }
    }
}
