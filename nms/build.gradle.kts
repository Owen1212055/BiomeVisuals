plugins {
    java
    id("io.papermc.paperweight.userdev") version "1.3.2"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.owen1212055"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.jpenilla.xyz/snapshots/")
}

dependencies {
    paperDevBundle("1.18.1-R0.1-SNAPSHOT")

    implementation(project(":api")) {
        isTransitive = false
    }

    implementation("xyz.jpenilla:reflection-remapper:0.1.0-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}