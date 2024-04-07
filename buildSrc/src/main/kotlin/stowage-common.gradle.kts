import semele.quinn.stowage.plugin.Versions

plugins {
    id("stowage-generic")
}

dependencies {
    modRuntimeOnly("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
}
