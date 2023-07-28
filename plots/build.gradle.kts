import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
}

group = "com.github.valb3r"
version = "0.0.2-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.jzy3d.org/releases/")
}

dependencies {
    implementation("org.jzy3d:jzy3d-native-jogl-swing:2.2.1")
    implementation("org.apache.commons:commons-math3:3.6.1")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}