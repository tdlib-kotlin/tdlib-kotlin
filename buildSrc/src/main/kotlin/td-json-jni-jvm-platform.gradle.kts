plugins {
    id("template.multiplatform.empty")
}

val nativeProject = evaluationDependsOn(":td-json-jni-jvm-native")

kotlin {
    jvm()
}

configureNativePublishing {
    nativeProject
        .extensions
        .getByName<CppLibrary>("library")
        .binaries
        .get { it.name.startsWith("mainRelease") }
        .flatMap { (it as CppSharedLibrary).linkFile }
}
