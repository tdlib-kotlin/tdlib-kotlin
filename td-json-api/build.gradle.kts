import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    template.multiplatform.all
}

val os = OperatingSystem.current()!!
val osLinkPath = when {
    os.isLinux  -> "/home/oleg/IdeaProjects/learn/td/built/tdlib/linux/lib"//"/home/linuxbrew/.linuxbrew/lib"
    os.isMacOsX -> "/opt/homebrew/lib"
    else        -> TODO("not supported yet")
}

kotlin {
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jvmMain {
            dependencies {
                api(projects.tdJsonApiJniLinux)
                api(projects.tdJsonApiJniMacos)
                api(projects.tdJsonApiJniWindows)
            }
        }
        androidMain {
            dependencies {
                api(projects.tdJsonApiJniAndroid)
            }
        }

        androidTest {
            dependencies {
                implementation("androidx.test:runner:1.4.0")
            }
        }

        targets.all {
            if (this !is KotlinNativeTarget) return@all

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
