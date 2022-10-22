plugins {
    id(Plugins.AndroidApplication.name)
    id(Plugins.KotlinAndroid.name)
    id(Plugins.Hilt.name)
    id(Plugins.SafeArgs.name)

    kotlin(Plugins.Kapt.name)
}

android {
    compileSdk = DefaultConfig.targetSdk

    defaultConfig {
        applicationId = DefaultConfig.id
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName
        testInstrumentationRunner = DefaultConfig.instrumentationRunner
    }

    buildTypes {
        getByName(BuildTypes.release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(ProGuard.optimizeFile),
                ProGuard.rulesFile
            )
        }
    }
    buildFeatures {
        viewBinding = true
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
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Google.material)
    implementation(Dependencies.Google.hilt)
    kapt(Dependencies.Google.hiltCompiler)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espressoCore)
}
