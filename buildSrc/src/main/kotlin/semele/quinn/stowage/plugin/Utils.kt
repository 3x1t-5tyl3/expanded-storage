package semele.quinn.stowage.plugin

import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun DependencyHandlerScope.neoForge(version: String) {
    add("neoForge", version)
}

private fun DependencyHandlerScope.compileOnly(dependency: Any) {
    add("compileOnly", dependency)
}

private fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandlerScope.include(dependency: Any) {
    add("include", dependency)
}

private val Project.sourceSets: SourceSetContainer get() {
    return extensions.getByName("sourceSets") as SourceSetContainer
}

private val SourceSetContainer.main: NamedDomainObjectProvider<SourceSet> get() = named("main")

private val SourceSet.kotlin: SourceDirectorySet get() = extensions.getByName("kotlin") as SourceDirectorySet

fun Project.includeFromCommon(otherPartialName: String) {
    val path = parent!!.childProjects.asSequence().first { it.key.contains(otherPartialName) }.value.path

    evaluationDependsOn(path)

    dependencies {
        compileOnly(project(path))
    }

    val excludeNeoSpec = Spec<Task> { task -> !task.name.startsWith("neo") }

    tasks.withType<JavaCompile>().matching(excludeNeoSpec).configureEach {
        source(project(path).sourceSets.main.map { it.java })
    }

    tasks.withType<KotlinCompile>().matching(excludeNeoSpec).configureEach {
        source(project(path).sourceSets.main.map { it.kotlin })
    }

    tasks.withType<ProcessResources>().matching(excludeNeoSpec).configureEach {
        from(project(path).sourceSets.main.map { it.resources }) {
            exclude("fabric.mod.json")
        }
    }
}

fun Project.includeFromModule(otherPartialName: String) {
    val target = rootProject.childProjects.asSequence().first { it.key.contains(otherPartialName) }.value.childProjects.asSequence().first { it.key == name }.value
    evaluationDependsOn(target.path)

    dependencies {
        implementation(project(path = target.path, configuration = "namedElements"))

        if (name != "a-common" && name != "c-thread") {
            include(project(path = target.path, configuration = "namedElements"))
        }
    }
}