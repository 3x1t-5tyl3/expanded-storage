package semele.quinn.stowage.plugin

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.neoForge(version: String) {
    add("neoForge", version)
}