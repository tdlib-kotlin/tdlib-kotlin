import org.gradle.internal.jvm.*
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.*
import org.jetbrains.kotlin.gradle.targets.native.tasks.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    `cpp-library`
}

kotlin {
    jvm()
    val nativeTargets = listOf(
        linuxX64(),
        macosX64(),
        macosArm64()
    )

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by creating
        val nativeTest by creating

        nativeTargets.forEach {
            getByName("${it.name}Main").dependsOn(nativeMain)
            getByName("${it.name}Test").dependsOn(nativeTest)

            val main by it.compilations.getting {
                val tdlib by cinterops.creating {
                    defFile(project.file("src/nativeMain/interop/tdlib.def"))
                }
            }
        }
    }
}

tasks.named<KotlinNativeTest>("linuxX64Test") {
    environment("LD_LIBRARY_PATH", "/home/linuxbrew/.linuxbrew/lib")
}
//linkerOpts.osx = -L/opt/homebrew/lib -ltdjson
//compilerOpts.osx = -I/opt/homebrew/include
library {
    targetMachines.add(machines.macOS.x86_64)
    source.from(file("src/jvmMain/cpp"))
    binaries.configureEach {
        compileTask.get().apply {
            compilerArgs.addAll(
                "-I/opt/homebrew/include",
                "-I${Jvm.current().javaHome.canonicalPath}/include",
                "-I${Jvm.current().javaHome.canonicalPath}/include/darwin",
            )
        }
    }
}

tasks.named<KotlinJvmTest>("jvmTest") {
    val sharedLib = library.developmentBinary.get() as CppSharedLibrary
    dependsOn(sharedLib.linkTask)
    systemProperty("java.library.path", sharedLib.linkFile.get().asFile.parentFile)
}
