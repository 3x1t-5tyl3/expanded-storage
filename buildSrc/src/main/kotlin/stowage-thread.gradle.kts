import org.gradle.kotlin.dsl.dependencies
import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeFromCommon

plugins {
    id("stowage-generic")
}

includeFromCommon("common")

dependencies {
    modLocalRuntime(modCompileOnly("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")!!)
    modLocalRuntime(modCompileOnly("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")!!)
    modLocalRuntime(modCompileOnly("net.fabricmc:fabric-language-kotlin:${Versions.FABRIC_KOTLIN}")!!)
}
