plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
}

android {
    namespace = "ir.alirezaivaz.tablericons"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

/**
 * Generate an object that holds list of all icons
 */
task("generateIconsObject") {
    val drawableDir = File("${projectDir}/src/main/res/drawable")
    val iconObjectCode = buildString {
        appendLine("package ir.alirezaivaz.tablericons")
        appendLine()
        appendLine("object TablerIcons {")
        drawableDir.listFiles()?.sorted()?.forEach {
            val variableName = it.name.toVariableName()
            appendLine("    val $variableName = R.drawable.${it.nameWithoutExtension}")
        }
        appendLine("}")
    }
    val iconObjectFile = File("${projectDir}/src/main/kotlin/ir/alirezaivaz/tablericons/TablerIcons.kt")
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

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "ir.alirezaivaz"
            artifactId = "tablericons"
            version = "1.12.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
