plugins {
    kotlin("jvm") version "1.9.0"
}

group = "org.example"
version = "0.0.4-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://maven.jzy3d.org/releases/")
}

dependencies {
    implementation(project(":data-adapters"))
    implementation(project(":plots"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}