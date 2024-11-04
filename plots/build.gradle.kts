import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
}

group = "com.github.valb3r"
version = "0.0.13-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.jzy3d.org/releases/")
}

dependencies {
    implementation("org.jzy3d:jzy3d-native-jogl-swing:2.2.1") {
        exclude(group = "org.apache.logging.log4j", module = "log4j-core")
    }

    implementation("org.jzy3d:jzy3d-emul-gl-awt:2.2.1")  {
        exclude(group = "org.apache.logging.log4j", module = "log4j-core")
    }

    implementation("org.apache.commons:commons-math3:3.6.1")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}