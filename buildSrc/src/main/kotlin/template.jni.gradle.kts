plugins {
//    `java-library`
    id("template.multiplatform")
}

kotlin {
    jvm()
}

val library = evaluationDependsOn(":td-json-jni-cpp").extensions.getByName<CppLibrary>("library")

//TODO: better way to get correct binary
// only one binary is available for now on each platform
val releaseBinary = library.binaries.get {
    it.name.startsWith("mainRelease") && when {
        it.targetMachine.operatingSystemFamily.isLinux   -> project.name.endsWith("linux")
        it.targetMachine.operatingSystemFamily.isMacOs   -> project.name.endsWith("macos")
        it.targetMachine.operatingSystemFamily.isWindows -> project.name.endsWith("windows")
        else                                             -> error("Unknown platform")
    }
}

//TODO: how it will be better to manage this
tasks.withType<AbstractPublishToMaven> {
    onlyIf {
        releaseBinary.isPresent
    }
}

tasks.named<org.gradle.api.tasks.bundling.Jar>("jvmJar") {
    val binary = releaseBinary.orNull ?: return@named
    from(
        (binary as CppSharedLibrary).linkFile.map { it.asFile.canonicalPath }
    ) {
        into("lib")
    }
}
