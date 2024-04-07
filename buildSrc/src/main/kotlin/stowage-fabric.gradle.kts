import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeCodeFrom

plugins {
    id("stowage-generic")
}

includeCodeFrom("common")
includeCodeFrom("thread")

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")
}
