plugins {
    id(Plugins.AndroidLibrary.name)
    id(Plugins.KotlinAndroid.name)
}

android {
    compileSdk = DefaultConfig.targetSdk

    defaultConfig {
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Options.compileSourceCompatibility
        targetCompatibility = Options.compileTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = Options.kotlinJwmTarget
    }
}

dependencies {
    implementation(Dependencies.Coroutines.core)
    coreLibraryDesugaring(Dependencies.Desugaring.core)
}
