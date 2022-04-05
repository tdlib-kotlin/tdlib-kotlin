plugins {
    com.android.library
}

android {
    compileSdk = 30
    defaultConfig {
        minSdk = 21
        targetSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets.all {
        setRoot("android/$name")
    }
}
