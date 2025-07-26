plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
}

group = "org.example"
version = "1.0-SNAPSHOT"

val jdaVersion = "5.6.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("net.dv8tion:JDA:${jdaVersion}")
    implementation("org.reflections:reflections:0.10.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = "dev.comae"
            artifactId = "dein-artifact-name"
            version = version

            pom {
                name.set("Mein Projekt")
                description.set("Beschreibung deines Projekts")
                url.set("https://github.com/deinuser/deinprojekt")
            }
        }
    }

    repositories {
        maven {
            name = "MeinServer"
            url = uri("https://mein.maven.server/repository/maven-releases/") // oder snapshot repo

            credentials {
                username = findProperty("mavenUser") as String? ?: System.getenv("MAVEN_USERNAME")
                password = findProperty("mavenPassword") as String? ?: System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}