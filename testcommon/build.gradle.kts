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
        freeCompilerArgs += Options.freeCompilerArgs
    }
}

dependencies {
    implementation(project(":common"))
    implementation(Dependencies.Coroutines.core)
    coreLibraryDesugaring(Dependencies.Desugaring.core)

    implementation(Dependencies.Test.junit)
    implementation(Dependencies.Test.arch)
    implementation(Dependencies.Test.mockito)
    implementation(Dependencies.Test.mockitoKotlin)
    implementation(Dependencies.Test.coroutines)
}
