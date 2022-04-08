plugins {
    id("template.multiplatform.empty")
}

kotlin {
    jvm()
}

configureNativePublishing {
    when (JvmNativePlatform.current) {
        JvmNativePlatform.Linux   -> "/home/oleg/IdeaProjects/learn/td/built/tdlib/linux/lib/libtdjson.so"
        JvmNativePlatform.Macos   -> TODO("not supported yet")
        JvmNativePlatform.Windows -> TODO("not supported yet")
    }
}
