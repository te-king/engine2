import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
}

group = "com.king"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.runtime)

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.3"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-opengl")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-macos-arm64")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-macos-arm64")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-macos-arm64")
}

kotlin {
    jvmToolchain(17)
    compilerOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = rootProject.name
            packageVersion = "1.0.0"
        }
    }
}