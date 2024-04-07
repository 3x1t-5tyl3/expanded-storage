package semele.quinn.stowage.plugin

import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources

fun DependencyHandlerScope.neoForge(version: String) {
    add("neoForge", version)
}

private fun DependencyHandlerScope.compileOnly(dependency: Any) {
    add("compileOnly", dependency)
}

private val Project.sourceSets: SourceSetContainer get() {
    return extensions.getByName("sourceSets") as SourceSetContainer
}

private val SourceSetContainer.main: NamedDomainObjectProvider<SourceSet> get() = named("main")

fun Project.includeCodeFrom(otherPartialName: String) {
    val path = parent!!.childProjects.asSequence().first { it.key.contains(otherPartialName) }.value.path

    evaluationDependsOn(path)

    dependencies {
        compileOnly(project(path))
    }

    val excludeNeoSpec = Spec<Task> { task -> !task.name.startsWith("neo") }

    tasks.withType<JavaCompile>().matching(excludeNeoSpec).configureEach {
        source(project(path).sourceSets.main.map { it.java })
    }

    tasks.withType<ProcessResources>().matching(excludeNeoSpec).configureEach {
        from(project(path).sourceSets.main.map { it.resources }) {
            exclude("fabric.mod.json")
        }
    }
}