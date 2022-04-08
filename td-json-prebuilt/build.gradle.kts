import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    template.android.library
    template.multiplatform.empty
}

kotlin {
    configureAndroid()
    configureJvm()
    configureNative()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.tdJson)
            }
        }

        val jniMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(projects.tdJsonJni)
            }
        }
        val jniTest by creating {
            dependsOn(commonTest.get())
        }
        val jvmMain by getting {
            dependsOn(jniMain)
            dependencies {
                api(projects.tdJsonPrebuiltJvmLinuxX64)
                api(projects.tdJsonPrebuiltJvmMacosX64)
                api(projects.tdJsonPrebuiltJvmWindowsX64)
            }
        }
        val jvmTest by getting {
            dependsOn(jniTest)
        }
        val androidMain by getting {
            dependsOn(jniMain)
        }
        val androidTest by getting {
            dependsOn(jniTest)
        }
    }

    targets.all {
        if (this !is KotlinNativeTarget) return@all

        val main by compilations.getting {
            val tdlib by cinterops.creating {
                //TODO: how to set properties from def dynamicly or use relative path at least?
                defFile(file("native/main/interop/tdlib.def"))
//                staticLibraries.linux = libtdjson.so
//                libraryPaths.linux = /home/oleg/IdeaProjects/learn/td/built/tdlib/linux/lib
//                linkerOpts.linux = -L/home/oleg/IdeaProjects/learn/td/built/tdlib/linux/lib -ltdjson
            }
        }
    }
}
