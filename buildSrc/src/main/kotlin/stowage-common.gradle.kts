import semele.quinn.stowage.plugin.Versions

plugins {
    id("stowage-generic")
}

dependencies {
    modRuntimeOnly("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")

    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}")
}
