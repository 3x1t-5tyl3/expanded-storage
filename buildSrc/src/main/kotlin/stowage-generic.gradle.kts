import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import semele.quinn.stowage.plugin.Constants
import semele.quinn.stowage.plugin.Versions

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("dev.architectury.loom")
}

group = "semele.quinn.stowage.${parent!!.name.substring(2)}"
version = Versions.STOWAGE
base.archivesName = Constants.MOD_ID + "-" + parent!!.name.substring(2) +  "-" + name.substring(2)

loom {
    silentMojangMappingsLicense()
}

repositories {
    mavenCentral()

    maven {
        name = "ParchmentMC Maven"
        url = uri("https://maven.parchmentmc.org/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Versions.PARCHMENT}")
    })
}

java.toolchain.languageVersion = JavaLanguageVersion.of(Versions.JAVA.ordinal + 1)

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = Versions.JAVA.ordinal + 1
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = Versions.JAVA.toString()
    }

    named<ProcessResources>("processResources") {
        inputs.properties(mutableMapOf(
            "mod_version" to Versions.STOWAGE
        ))

        // todo: 1.20.5, remove META-INF/mods.toml
        filesMatching(listOf("fabric.mod.json", "quilt.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
            expand(inputs.properties)
        }
    }
}