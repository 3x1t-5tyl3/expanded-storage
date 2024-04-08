import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeFromCommon
import semele.quinn.stowage.plugin.neoForge

plugins {
    id("stowage-generic")
}

includeFromCommon("common")

repositories {
    maven {
        name = "NeoForge Maven"
        url = uri("https://maven.neoforged.net/releases/")
    }

    exclusiveContent {
        forRepository {
            maven {
                name = "Kotlin for Forge"
                url = uri("https://thedarkcolour.github.io/KotlinForForge/")
            }
        }

        filter {
            includeGroup("thedarkcolour")
        }
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${Versions.NEOFORGE}")
    implementation("thedarkcolour:kotlinforforge-neoforge:${Versions.KOTLIN_FOR_FORGE}")
}
