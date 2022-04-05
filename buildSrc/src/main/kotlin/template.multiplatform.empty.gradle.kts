plugins {
    org.jetbrains.kotlin.multiplatform
    id("template.publication")
}

kotlin {
    explicitApi()
    sourceSets.all {
        val (targetName, suffix) = if (name.startsWith("android")) {
            "android" to name.substringAfter("android").decapitalize()
        } else {
            val suffixIndex = name.indexOfLast { it.isUpperCase() }
            name.substring(0, suffixIndex) to name.substring(suffixIndex).decapitalize()
        }

        this.kotlin.setSrcDirs(listOf("$targetName/$suffix/kotlin"))
        this.resources.setSrcDirs(listOf("$targetName/$suffix/resources"))

        languageSettings {
            progressiveMode = true
            optIn("kotlin.RequiresOptIn")
        }
    }
}
