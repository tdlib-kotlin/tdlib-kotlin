import org.gradle.internal.jvm.*

plugins {
    `cpp-library`
}

library {
    baseName.set("tdjsonjni")
    source.from(file("src"))
    targetMachines.set(
        listOf(
            machines.linux.x86_64,
            machines.macOS.x86_64,
            machines.windows.x86_64
        )
    )
    binaries.configureEach {
        this as CppSharedLibrary

        val os = this.targetMachine.operatingSystemFamily

        val osJvmHeadersSuffix = when {
            os.isLinux -> "linux"
            os.isMacOs -> "darwin"
            else       -> TODO("not supported yet")
        }

        val osIncludePath = when {
            os.isLinux -> "/home/linuxbrew/.linuxbrew/include"
            os.isMacOs -> "/opt/homebrew/include"
            else       -> TODO("not supported yet")
        }

        val osLinkPath = when {
            os.isLinux -> "/home/linuxbrew/.linuxbrew/lib"
            os.isMacOs -> "/opt/homebrew/lib"
            else       -> TODO("not supported yet")
        }

        val jvmPath = Jvm.current().javaHome.canonicalPath

        linkTask.get().linkerArgs.addAll(
            "-L$osLinkPath",
            "-ltdjson"
        )

        compileTask.get().compilerArgs.addAll(
            "-I$osIncludePath",
            "-I$jvmPath/include",
            "-I$jvmPath/include/$osJvmHeadersSuffix",
        )
    }
}
