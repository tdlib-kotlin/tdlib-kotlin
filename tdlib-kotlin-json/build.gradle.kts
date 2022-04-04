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

//TODO: add built library to jar
// decide on multiple jars on JVM - per linux/windows/macos
library {
    source.from(file("src/jvmMain/cpp"))
    binaries.configureEach {
        this as CppSharedLibrary
        linkTask.get().apply {
            linkerArgs.addAll(
                "-L/home/linuxbrew/.linuxbrew/lib",
                "-ltdjson"
            )
        }
        compileTask.get().apply {
            compilerArgs.addAll(
                "-I/home/linuxbrew/.linuxbrew/include",
                "-I${Jvm.current().javaHome.canonicalPath}/include",
                "-I${Jvm.current().javaHome.canonicalPath}/include/linux",
            )
        }
    }
}


tasks.named<KotlinJvmTest>("jvmTest") {
    val lib = library.developmentBinary.get() as CppSharedLibrary
    dependsOn(lib.linkTask)
    val libPath = lib.linkFile.get().asFile.parentFile.canonicalPath
    val tdlibPath = "/home/linuxbrew/.linuxbrew/lib"
    systemProperty("java.library.path", listOf(libPath, tdlibPath).joinToString(":"))
}
