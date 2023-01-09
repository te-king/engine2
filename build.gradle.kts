import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.king"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
            kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.1")

                implementation("org.lwjgl:lwjgl:3.3.1")
                implementation("org.lwjgl:lwjgl-glfw:3.3.1")
                implementation("org.lwjgl:lwjgl-opengl:3.3.1")
                runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-macos")
                runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-macos")
                runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-macos")
//                runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-windows")
//                runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-windows")
//                runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-windows")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "engine2"
            packageVersion = "1.0.0"
        }
    }
}