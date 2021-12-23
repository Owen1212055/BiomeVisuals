pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        mavenCentral()
    }
}

rootProject.name = "BiomeVisuals"
include("api", "nms", "plugin")
