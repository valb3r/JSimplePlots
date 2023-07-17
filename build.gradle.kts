import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.valb3r.jsimpleplots"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.jzy3d.org/releases/")
}

dependencies {
    implementation("org.jzy3d:jzy3d-native-jogl-swing:2.2.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}