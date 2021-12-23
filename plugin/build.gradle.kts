import java.nio.file.*

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.owen1212055"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    compileOnly(project(":nms"))

    implementation("org.bstats:bstats-bukkit:2.2.1")
}


tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)

        val mainFile = file("../nms/build/libs/nms-1.0.jar").toPath()
        if (Files.exists(mainFile)) {
            Files.copy(
                mainFile,
                file("src/main/resources/nms-1.0.jar").toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }
    }

    shadowJar {
        dependencies {
            exclude(project(":nms")) // Included later via a spigot mapped jar.
            relocate("org.bstats", "com.owen1212055.biomevisuals.libs.bstats")
        }
    }

}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}




