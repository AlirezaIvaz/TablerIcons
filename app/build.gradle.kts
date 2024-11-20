plugins {
    id("com.android.application")
    kotlin("android")
}

val githubUrl = "https://github.com/AlirezaIvaz/TablerIcons"
val issuesUrl = "$githubUrl/issues"

android {
    namespace = "ir.alirezaivaz.tablericons.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.alirezaivaz.tablericons.demo"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        buildConfigField("String", "GITHUB_REPO_URL", "\"$githubUrl\"")
        buildConfigField("String", "GITHUB_ISSUES_URL", "\"$issuesUrl\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += "distributor"
    productFlavors {
        create("github") {
            versionNameSuffix = "-GH"
            buildConfigField("String", "RATE_INTENT", "\"\"")
            buildConfigField("String", "APPS_INTENT", "\"\"")
        }
        create("cafebazaar") {
            dimension = "distributor"
            versionNameSuffix = "-CB"
            buildConfigField("String", "RATE_INTENT", "\"bazaar://details?id=${defaultConfig.applicationId}\"")
            buildConfigField("String", "APPS_INTENT", "\"bazaar://collection?slug=by_author&aid=alirezaivaz\"")
        }
        create("myket") {
            dimension = "distributor"
            versionNameSuffix = "-MK"
            buildConfigField("String", "RATE_INTENT", "\"myket://comment?id=${defaultConfig.applicationId}\"")
            buildConfigField("String", "APPS_INTENT", "\"myket://developer/${defaultConfig.applicationId}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":library"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}