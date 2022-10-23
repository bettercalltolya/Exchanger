plugins {
    id(Plugins.AndroidApplication.name) version Plugins.AndroidApplication.version apply false
    id(Plugins.AndroidLibrary.name) version Plugins.AndroidLibrary.version apply false
    id(Plugins.KotlinAndroid.name) version Plugins.KotlinAndroid.version apply false
    id(Plugins.Hilt.name) version  Plugins.Hilt.version apply false
    id(Plugins.SafeArgs.name) version Plugins.SafeArgs.version apply false

    kotlin(Plugins.Serialization.name) version Plugins.Serialization.version apply false
    kotlin(Plugins.Kapt.name) version Plugins.Kapt.version apply false
}
