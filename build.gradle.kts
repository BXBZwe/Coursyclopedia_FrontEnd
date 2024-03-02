buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7") // Use the latest version available
        classpath("com.google.gms:google-services:4.4.1") // Check for the latest version


    }
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}
