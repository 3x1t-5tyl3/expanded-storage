import org.gradle.kotlin.dsl.dependencies
import semele.quinn.stowage.plugin.Versions

plugins {
    id("stowage-generic")
}

evaluationDependsOn(project.path.replace("c-thread", "a-common"))

dependencies {
    modLocalRuntime(modCompileOnly("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")!!)
    modLocalRuntime(modCompileOnly("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")!!)
}
