plugins {
    java
    kotlin("jvm") version "1.8.0-Beta"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.protobuf") version "0.8.18"
    kotlin("kapt") version "1.7.21"
}

group = "eu.suro.announcer"
version = "1.0.0"


val gitlabToken = if (System.getenv("CI_TOKEN") != null) {
    System.getenv("CI_TOKEN")
} else {
    findProperty("gitLabPrivateToken")
}

repositories{
    mavenCentral()
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven {
        url = uri( "https://gitlab.hyneo.ru/api/v4/groups/39/-/packages/maven")
        credentials(HttpHeaderCredentials::class) {
            name = "Private-Token"
            value = "$gitlabToken"
        }
        authentication {
            create("header", HttpHeaderAuthentication::class)
        }
    }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") } // This lets gradle find the BungeeCord files online
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://jitpack.io")
    maven("https://repo.panda-lang.org/releases")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots1"
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    compileJava{
        options.encoding =  "UTF-8"
    }

    processResources {
        filesMatching("*.json") {
            expand(project.properties)
        }
    }

    shadowJar {
        archiveClassifier.set("")
        exclude("**/*.kotlin_metadata")
    }
}

dependencies {
    compileOnly(libs.velocity)
    compileOnly("org.yaml:snakeyaml:1.33")
    implementation(libs.configlib.yaml)
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
}
