plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
}

group = "dev.comae"
version = "1.1.0"

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
            artifactId = "jdaCommandManager"
            version = version

            pom {
                name.set("JDA Command Manager")
                description.set("A very simple command manager/helper for JDA")
                url.set("https://github.com/C0MaE/JDACommandManager")
            }
        }
    }

    repositories {
        maven {
            name = "OwnServer"
            url = uri("https://repo.comae.dev/repository/maven-releases/") // oder snapshot repo

            credentials {
                username = findProperty("mavenUser") as String? ?: System.getenv("MAVEN_USERNAME")
                password = findProperty("mavenPassword") as String? ?: System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}