import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeFromCommon

plugins {
    id("stowage-generic")
}

includeFromCommon("common")
includeFromCommon("thread")

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${Versions.FABRIC_KOTLIN}")
}
