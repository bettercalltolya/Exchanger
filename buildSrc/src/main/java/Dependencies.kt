import org.gradle.api.JavaVersion

object Plugins {
    object AndroidApplication {
        const val name = "com.android.application"
        const val version = "7.3.0"
    }

    object AndroidLibrary {
        const val name = "com.android.library"
        const val version = "7.3.0"
    }

    object KotlinAndroid {
        const val name = "org.jetbrains.kotlin.android"
        const val version = "1.7.20"
    }

    object Kapt {
        const val name = "kapt"
        const val version = "1.7.20"
    }

    object Serialization {
        const val name = "plugin.serialization"
        const val version = "1.7.20"
    }

    object Hilt {
        const val name = "com.google.dagger.hilt.android"
        const val version = "2.44"
    }

    object SafeArgs {
        const val name = "androidx.navigation.safeargs.kotlin"
        const val version = "2.5.2"
    }
}

object DefaultConfig {
    const val id = "com.bettercalltolya.kevintask"
    const val versionCode = 1
    const val versionName = "1.0"
    const val minSdk = 23
    const val targetSdk = 33
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildTypes {
    const val release = "release"
}

object ProGuard {
    const val optimizeFile = "proguard-android-optimize.txt"
    const val rulesFile = "proguard-rules.pro"
}

object Options {
    val compileSourceCompatibility = JavaVersion.VERSION_11
    val compileTargetCompatibility = JavaVersion.VERSION_11
    const val kotlinJwmTarget = "11"
    val freeCompilerArgs = listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
    )
}

object Dependencies {

    private object Versions {
        const val coreKtx = "1.9.0"
        const val appCompat = "1.5.1"
        const val material = "1.6.1"
        const val hilt = "2.44"
        const val navigation = "2.5.2"
        const val room = "2.4.3"
        const val coroutines = "1.3.9"
        const val serialization = "1.4.1"
        const val paging = "3.1.1"
        const val retrofit = "2.9.0"
        const val okHttpLoggingInterceptor = "3.4.1"
        const val kotlinSerializationConverter = "0.8.0"

        const val desugaring = "1.1.5"

        const val testJunit = "4.13.2"

        const val androidTestJunit = "1.1.3"
        const val androidTestEspressoCore = "3.4.0"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

        const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui:${Versions.navigation}"

        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

        const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Serialization {
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
        const val kotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverter}"
    }

    object Desugaring {
        const val core = "com.android.tools:desugar_jdk_libs:${Versions.desugaring}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.testJunit}"
    }

    object AndroidTest {
        const val junit = "androidx.test.ext:junit:${Versions.androidTestJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.androidTestEspressoCore}"
    }
}
