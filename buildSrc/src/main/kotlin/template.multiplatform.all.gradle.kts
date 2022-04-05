import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    id("template.android.library")
    id("template.multiplatform.empty")
}

kotlin {
    configureAndroid()
    configureJvm()
    linuxX64()
    macosX64()

    sourceSets {
        val nativeMain by creating {
            dependsOn(commonMain.get())
        }

        val nativeTest by creating {
            dependsOn(commonTest.get())
        }

        targets.all {
            if (this !is KotlinNativeTarget) return@all

            getByName("${name}Main").dependsOn(nativeMain)
            getByName("${name}Test").dependsOn(nativeTest)
        }
    }
}
