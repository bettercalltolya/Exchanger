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
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Google.hilt)
    kapt(Dependencies.Google.hiltCompiler)

    implementation(Dependencies.Retrofit.core)

    implementation(Dependencies.AndroidX.room)
    implementation(Dependencies.AndroidX.roomKtx)
    kapt(Dependencies.AndroidX.roomCompiler)
}
