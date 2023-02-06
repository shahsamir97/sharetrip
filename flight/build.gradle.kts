plugins {
    id("maven-publish")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "net.sharetrip.flight"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    buildTypes {
        release {
            manifestPlaceholders["enableCrashReporting"] = "true"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    /*flavorDimensions += listOf("platform")

    productFlavors {
        create("sharetrip") {
            dimension = "platform"
        }
        create("banglalink") {
            dimension = "platform"
        }
    }*/

    dataBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":payment"))


    project.core()
    project.coroutines()
    project.retrofit()

    project.lifecycle()
    project.navigation()

    project.converter()
    project.storage()
    project.mixed()
    project.imageLibrary()
    project.annotationProcessor()
    implementation("com.facebook.shimmer:shimmer:0.5.0")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.jubayar"
                artifactId = "flight"
                version = "0.9.2"
            }
        }
    }
}
