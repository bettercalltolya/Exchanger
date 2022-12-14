plugins {
    id(Plugins.AndroidLibrary.name)
    id(Plugins.KotlinAndroid.name)
    id(Plugins.Hilt.name)
    kotlin(Plugins.Kapt.name)
    kotlin(Plugins.Serialization.name)
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
    implementation(project(":common"))

    implementation(Dependencies.Serialization.core)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Google.hilt)
    kapt(Dependencies.Google.hiltCompiler)

    testImplementation(project(":testcommon"))
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.arch)
    testImplementation(Dependencies.Test.mockito)
    testImplementation(Dependencies.Test.mockitoKotlin)
    testImplementation(Dependencies.Test.coroutines)
}
