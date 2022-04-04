import org.gradle.internal.jvm.*
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    template.multiplatform
    `cpp-library`
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

library {
    //TODO: set single targetMachine
    source.from(file("jvm/main/cpp"))
    val jvmPath = Jvm.current().javaHome.canonicalPath
    val osJvmSuffix = when {
        os.isLinux  -> "linux"
        os.isMacOsX -> "darwin"
        else        -> TODO("not supported yet")
    }
    val osIncludePath = when {
        os.isLinux  -> "/home/linuxbrew/.linuxbrew/include"
        os.isMacOsX -> "/opt/homebrew/include"
        else        -> TODO("not supported yet")
    }
    binaries.configureEach {
        this as CppSharedLibrary

        linkTask.get().linkerArgs.addAll(
            "-L$osLinkPath",
            "-ltdjson"
        )

        compileTask.get().compilerArgs.addAll(
            "-I$osIncludePath",
            "-I$jvmPath/include",
            "-I$jvmPath/include/$osJvmSuffix",
        )
    }
}

tasks.withType<KotlinNativeHostTest> {
    environment("LD_LIBRARY_PATH", osLinkPath)
}


tasks.named<KotlinJvmTest>("jvmTest") {
    val lib = library.developmentBinary.get() as CppSharedLibrary
    dependsOn(lib.linkTask)
    systemProperty("java.library.path", listOf(lib.linkFile.get().asFile.parentFile.canonicalPath, osLinkPath).joinToString(":"))
}
