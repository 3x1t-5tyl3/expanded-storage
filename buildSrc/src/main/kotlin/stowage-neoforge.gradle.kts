import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeCodeFrom
import semele.quinn.stowage.plugin.neoForge

plugins {
    id("stowage-generic")
}

includeCodeFrom("common")

repositories {
    maven {
        name = "NeoForge Maven"
        url = uri("https://maven.neoforged.net/releases/")
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${Versions.NEOFORGE}")
}
