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
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)
    implementation(Dependencies.AndroidX.paging)
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Google.material)

    coreLibraryDesugaring(Dependencies.Desugaring.core)
    
    implementation(Dependencies.Google.hilt)
    kapt(Dependencies.Google.hiltCompiler)

    implementation(Dependencies.AndroidX.room)
    kapt(Dependencies.AndroidX.roomCompiler)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espressoCore)
}
