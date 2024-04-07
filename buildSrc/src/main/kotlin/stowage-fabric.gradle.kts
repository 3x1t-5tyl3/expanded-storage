import semele.quinn.stowage.plugin.Versions

plugins {
    id("stowage-generic")
}

evaluationDependsOn(project.path.replace("d-fabric", "a-common"))

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")
}
