plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "ir.alirezaivaz.tablericons"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.annotation:annotation:1.6.0")
}

/**
 * Generate an enum class that holds list of all icons
 */
task("generateIconsObject") {
    val drawableDir = File("${projectDir}/src/main/res/drawable")
    val iconObjectCode = buildString {
        appendLine("package ir.alirezaivaz.tablericons")
        appendLine()
        appendLine("enum class Icons(val resId: Int) {")
        append(drawableDir.listFiles()?.sorted()?.joinToString(separator = ",\n") {
            val variableName = it.name.toVariableName()
            "    $variableName(R.drawable.${it.nameWithoutExtension})"
        })
        appendLine(";")
        appendLine("}")
    }
    val iconObjectFile = File("${projectDir}/src/main/kotlin/ir/alirezaivaz/tablericons/Icons.kt")
    iconObjectFile.parentFile.mkdirs()
    iconObjectFile.writeText(iconObjectCode)
}

/**
 * Convert icon file name to variable name
 */
fun String.toVariableName(): String {
    return removePrefix("ic")
        .removeSuffix(".xml")
        .toCamelCase()
        .fixNumericName()
}

/**
 * Add an underscore to name of variable if it starts with number
 */
fun String.fixNumericName(): String {
    return if (this[0].isDigit()) "_$this" else this
}

/**
 * Convert variable name to camel case
 */
fun String.toCamelCase(): String {
    return split("_").joinToString("") { it.capitalize() }
}
