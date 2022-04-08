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

include("td-json") //v

include("td-json-jni") //v-td
module("td-json-jni-jvm-native", "td-json-jni")
module("td-json-jni-jvm-linux-x64", "td-json-jni")
module("td-json-jni-jvm-macos-x64", "td-json-jni")
//module("td-json-jni-jvm-macos-arm64", "td-json-jni")
module("td-json-jni-jvm-windows-x64", "td-json-jni")

include("td-json-prebuilt") //v-td
module("td-json-prebuilt-jvm-linux-x64", "td-json-prebuilt")
module("td-json-prebuilt-jvm-macos-x64", "td-json-prebuilt")
//module("td-json-prebuilt-jvm-macos-arm64", "td-json-prebuilt")
module("td-json-prebuilt-jvm-windows-x64", "td-json-prebuilt")

include("td-json-dynamic") //v-td


include("td-json-tests")


//td-api //v
//td-api-core //v-td
//td-api-user //v-td
//td-api-bot  //v-td
//td-api-test //v-td
