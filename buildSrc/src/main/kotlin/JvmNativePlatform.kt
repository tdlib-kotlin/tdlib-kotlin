import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.publish.maven.tasks.*
import org.gradle.internal.os.*
import org.gradle.kotlin.dsl.*
import org.gradle.language.cpp.*
import org.gradle.nativeplatform.*

enum class JvmNativePlatform {
    Linux,
    Macos,
    Windows;

    companion object {
        val current: JvmNativePlatform
            get() = when {
                OperatingSystem.current().isLinux   -> JvmNativePlatform.Linux
                OperatingSystem.current().isMacOsX  -> JvmNativePlatform.Macos
                OperatingSystem.current().isWindows -> JvmNativePlatform.Windows
                else                                -> error("Unknown platform")
            }
    }
}

val Project.platform: JvmNativePlatform
    get() = when {
        project.name.endsWith("linux-x64")   -> JvmNativePlatform.Linux
        project.name.endsWith("macos-x64")   -> JvmNativePlatform.Macos
        project.name.endsWith("windows-x64") -> JvmNativePlatform.Windows
        else                                 -> error("Unknown platform")
    }

val TargetMachine.platform: JvmNativePlatform
    get() = when {
        operatingSystemFamily.isLinux   -> JvmNativePlatform.Linux
        operatingSystemFamily.isMacOs   -> JvmNativePlatform.Macos
        operatingSystemFamily.isWindows -> JvmNativePlatform.Windows
        else                            -> error("Unknown platform")
    }

fun Project.configureNativePublishing(libraryFile: () -> Any) {

    //TODO: how it will be better to manage this
    tasks.withType<AbstractPublishToMaven> {
        onlyIf { project.platform == JvmNativePlatform.current }
    }

    tasks.named<org.gradle.api.tasks.bundling.Jar>("jvmJar") {
        if (project.platform != JvmNativePlatform.current) return@named

        from(libraryFile()) {
            into("lib")
        }
    }
}
