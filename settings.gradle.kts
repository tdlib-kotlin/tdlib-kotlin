enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

fun module(name: String, parentPath: String) {
    include(name)
    project(":$name").projectDir = file("$parentPath/$name")
}

include("td-json-api")
include("td-json")

//td-json-api - API for tdlib

// I - should include tdlib

//td-json - includes links to tdlib on all platforms

//  td-json-jvm-linux-x64
//  td-json-jvm-macos-x64
//  td-json-jvm-macos-arm64
//  td-json-jvm-windows-x64
//  td-json-jvm - includes all platforms, but artifact is still small

//td-json-android [I] - single AAR artifact for all architectures

//td-json-wasm [I] - experimental
//td-json-browser [I]
//td-json-node

//td-json-native

//td-json-native-desktop
//td-json-native-darwin //ios, watchos, tvos

//td-json-native
//  td-json-native-desktop
//    td-json-native-linux
//    td-json-native-macos
//    td-json-native-windows
//  td-json-native-ios [I]
//  td-json-native-watchos [I]
//  td-json-native-tvos [I]

//td-lib[prebuilt] - prebuilt native tdlib library per platform - TODO: decide on how to provide mpp

//td-api - generated API for specific tdlib version
