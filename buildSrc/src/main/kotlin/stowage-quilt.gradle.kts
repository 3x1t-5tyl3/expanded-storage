import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeCodeFrom

plugins {
    id("stowage-generic")
}

includeCodeFrom("common")
includeCodeFrom("thread")

repositories {
    maven {
        name = "QuiltMC Maven"
        url = uri("https://maven.quiltmc.org/repository/release/")
    }
}

dependencies {
    modImplementation("org.quiltmc:quilt-loader:${Versions.QUILT_LOADER}")
    modImplementation("org.quiltmc.quilted-fabric-api:quilted-fabric-api:${Versions.QUILT_API}")
}
