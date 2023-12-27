plugins {
    java
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.owen1212055"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation("xyz.jpenilla", "reflection-remapper", "0.1.0-SNAPSHOT")

    implementation(project(":api")) {
        isTransitive = false
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}