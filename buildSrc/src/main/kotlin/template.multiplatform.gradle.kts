plugins {
    org.jetbrains.kotlin.multiplatform
}

kotlin {
    explicitApi()
    sourceSets.all {
        val suffixIndex = name.indexOfLast { it.isUpperCase() }
        val targetName = name.substring(0, suffixIndex)
        val suffix = name.substring(suffixIndex).toLowerCase()

        this.kotlin.setSrcDirs(listOf("$targetName/$suffix/kotlin"))
        this.resources.setSrcDirs(listOf("$targetName/$suffix/resources"))

        languageSettings {
            progressiveMode = true
            optIn("kotlin.RequiresOptIn")
        }
    }
}
