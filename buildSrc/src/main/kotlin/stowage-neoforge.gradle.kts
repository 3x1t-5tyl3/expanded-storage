import semele.quinn.stowage.plugin.Constants
import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeFromCommon
import semele.quinn.stowage.plugin.subprojectFromPartial
import semele.quinn.stowage.plugin.neoForge

plugins {
    id("stowage-generic")
}

includeFromCommon("common")

loom {
    runs {
        create("datagen") {
            data()

            programArg("--existing")
            programArg(file("src/main/resources").absolutePath)

            programArg("--existing")
            programArg(subprojectFromPartial("common").file("src/main/resources").absolutePath)


            programArg("--all")

            programArg("--mod")
            programArg(Constants.MOD_ID)

            programArg("--output")
            programArg(file("src/generated/resources").absolutePath)
        }
    }
}

sourceSets.main {
    resources.srcDir(file("src/generated/resources"))
}

repositories {
    maven {
        name = "NeoForge Maven"
        url = uri("https://maven.neoforged.net/releases/")
    }

    maven {
        name = "NeoForge PR Maven #787' "// https://github.com/neoforged/NeoForge/pull/787
        url = uri("https://prmaven.neoforged.net/NeoForge/pr787")

        content {
            includeModule("net.neoforged", "testframework")
            includeModule("net.neoforged", "neoforge")
        }
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
//    implementation("thedarkcolour:kotlinforforge-neoforge:${Versions.KOTLIN_FOR_FORGE}")

    "forgeRuntimeLibrary"(include(implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}")!!)!!)
    "forgeRuntimeLibrary"(include(implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")!!)!!)
    "forgeRuntimeLibrary"(include(implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}")!!)!!)
    "forgeRuntimeLibrary"(include(implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}")!!)!!)
}
