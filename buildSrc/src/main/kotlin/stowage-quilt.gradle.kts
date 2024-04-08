import semele.quinn.stowage.plugin.Versions
import semele.quinn.stowage.plugin.includeFromCommon

plugins {
    id("stowage-generic")
}

includeFromCommon("common")
includeFromCommon("thread")

repositories {
    maven {
        name = "QuiltMC Maven"
        url = uri("https://maven.quiltmc.org/repository/release/")
    }
}

dependencies {
    modImplementation("org.quiltmc:quilt-loader:${Versions.QUILT_LOADER}")
    modImplementation("org.quiltmc.quilted-fabric-api:quilted-fabric-api:${Versions.QUILT_API}")
    modImplementation("org.quiltmc.quilt-kotlin-libraries:quilt-kotlin-libraries:${Versions.QUILT_KOTLIN}")
}
