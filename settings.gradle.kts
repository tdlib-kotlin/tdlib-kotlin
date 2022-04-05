enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
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

//only bindings to tdlib
include("td-json-api")
module("td-json-api-jni", "td-json-api")
module("td-json-api-jni-android", "td-json-api")
module("td-json-api-jni-linux", "td-json-api")
module("td-json-api-jni-macos", "td-json-api")
module("td-json-api-jni-windows", "td-json-api")

//also contains tdlib
//include("td-json")
//module("td-json-jni", "td-json")
//module("td-json-jni-android", "td-json")
//module("td-json-jni-linux", "td-json")
//module("td-json-jni-macos", "td-json")
//module("td-json-jni-windows", "td-json")
