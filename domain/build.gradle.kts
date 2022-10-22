plugins {
    id(Plugins.AndroidLibrary.name)
    id(Plugins.KotlinAndroid.name)
    id(Plugins.Hilt.name)
    kotlin(Plugins.Kapt.name)
}

android {
    compileSdk = DefaultConfig.targetSdk

    defaultConfig {

        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
    }
    compileOptions {
        sourceCompatibility = Options.compileSourceCompatibility
        targetCompatibility = Options.compileTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = Options.kotlinJwmTarget
        freeCompilerArgs += Options.freeCompilerArgs
    }
}

dependencies {
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Google.hilt)
    kapt(Dependencies.Google.hiltCompiler)
}
