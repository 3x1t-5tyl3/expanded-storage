import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.neoForge

plugins {
    id("stowage-generic")
}

evaluationDependsOn(project.path.replace("b-neoforge", "a-common"))

repositories {
    maven {
        name = "NeoForge Maven"
        url = uri("https://maven.neoforged.net/releases/")
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${Versions.NEOFORGE}")
}
