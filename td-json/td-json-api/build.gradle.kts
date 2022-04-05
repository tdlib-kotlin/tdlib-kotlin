import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    template.multiplatform
}

val os = OperatingSystem.current()!!
val osLinkPath = when {
    os.isLinux  -> "/home/linuxbrew/.linuxbrew/lib"
    os.isMacOsX -> "/opt/homebrew/lib"
    else        -> TODO("not supported yet")
}

kotlin {
    jvm()
    linuxX64()
    macosX64()

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api(projects.tdJsonJniLinux)
                api(projects.tdJsonJniMacos)
                api(projects.tdJsonJniWindows)
            }
        }
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

            val main by compilations.getting {
                val tdlib by cinterops.creating {
                    //TODO: generate how to set properties from def dynamicly as with cpp library?
                    defFile(file("native/main/interop/tdlib.def"))
                }
            }
        }
    }
}

tasks.withType<KotlinNativeHostTest> {
    environment("LD_LIBRARY_PATH", osLinkPath)
}

tasks.named<KotlinJvmTest>("jvmTest") {
    systemProperty("java.library.path", osLinkPath)
}
