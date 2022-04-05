plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain {
        this as JavaToolchainSpec
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    implementation(buildLibs.build.kotlin)
    implementation(buildLibs.build.kotlinx.atomicfu)
    implementation(buildLibs.build.android)
}
