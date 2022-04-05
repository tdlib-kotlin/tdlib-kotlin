import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.configureAndroid() {
    android {
        publishAllLibraryVariants()
    }
}
fun KotlinMultiplatformExtension.configureJvm() {
    jvm {

    }
}
