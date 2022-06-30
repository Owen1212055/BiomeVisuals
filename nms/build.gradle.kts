plugins {
    java
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.owen1212055"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")

    implementation(project(":api")) {
        isTransitive = false
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}