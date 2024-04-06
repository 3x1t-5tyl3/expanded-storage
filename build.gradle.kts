import me.modmuss50.mpp.ReleaseType
import me.modmuss50.mpp.platforms.curseforge.CurseforgeOptions
import me.modmuss50.mpp.platforms.modrinth.ModrinthOptions
import org.codehaus.groovy.runtime.ProcessGroovyMethods
import semele.quinn.expandedstorage.plugin.Constants
import semele.quinn.expandedstorage.plugin.Versions
import semele.quinn.expandedstorage.plugin.dependency.FreezableDependencyList
import semele.quinn.expandedstorage.plugin.task.AbstractJsonTask
import semele.quinn.expandedstorage.plugin.task.AbstractRestrictedTask
import semele.quinn.expandedstorage.plugin.task.BuildModTask
import semele.quinn.expandedstorage.plugin.task.ReleaseModTask

plugins {
    id("me.modmuss50.mod-publish-plugin") version "0.5.1"
}

version = Versions.EXPANDEDSTORAGE

tasks.create(Constants.BUILD_MOD_TASK, BuildModTask::class.java)
val releaseTask = tasks.register(Constants.RELEASE_MOD_TASK, ReleaseModTask::class.java) {
    dependsOn(":publishMods")
}

gradle.taskGraph.whenReady {
    for (task in allTasks) {
        (task as? AbstractRestrictedTask)?.doChecks()
    }
}

evaluationDependsOnChildren()

val releaseType = if ("alpha" in Versions.EXPANDEDSTORAGE) {
    ReleaseType.ALPHA
} else if ("beta" in Versions.EXPANDEDSTORAGE) {
    ReleaseType.BETA
} else {
    ReleaseType.STABLE
}

val modChangelog = rootProject.file("changelog.md").readLines(Charsets.UTF_8) +
        listOf("", "Commit: https://github.com/quinn-semele/expanded-storage/commit/${getGitCommit()}")

val commonCurseForgeOptions = publishMods.curseforgeOptions {
    accessToken = providers.environmentVariable("QUINN_CF_TOKEN")
    projectId = "978068"
    projectSlug = "expanded-storage"

    clientRequired = true
    serverRequired = true

    minecraftVersions.addAll(Versions.SUPPORTED_GAME_VERSIONS)
    javaVersions.add(JavaVersion.VERSION_17)
}

val commonModrinthOptions = publishMods.modrinthOptions {
    accessToken = providers.environmentVariable("QUINN_MR_TOKEN")
    projectId = "jCCPlP3c"

    minecraftVersions.addAll(Versions.SUPPORTED_GAME_VERSIONS)
}

val fabricOptions = publishMods.publishOptions {
    modLoaders.add("fabric")
    displayName = "ES Fabric ${Versions.EXPANDEDSTORAGE}"
    version = "${Versions.EXPANDEDSTORAGE}+fabric"
    file = project(":fabric").tasks.named<AbstractJsonTask>("minJar").map { it.archiveFile.get() }
}

val forgeOptions = publishMods.publishOptions {
    modLoaders.add("forge")
    modLoaders.add("neoforge")
    displayName = "ES Neo/Forge ${Versions.EXPANDEDSTORAGE}"
    version = "${Versions.EXPANDEDSTORAGE}+forge"
    file = project(":forge").tasks.named<AbstractJsonTask>("minJar").map { it.archiveFile.get() }
}

val quiltOptions = publishMods.publishOptions {
    modLoaders.add("quilt")
    displayName = "ES Quilt ${Versions.EXPANDEDSTORAGE}"
    version = "${Versions.EXPANDEDSTORAGE}+quilt"
    file = project(":quilt").tasks.named<AbstractJsonTask>("minJar").map { it.archiveFile.get() }
}

val threadDependencies = project(":thread").extra["mod_dependencies"] as FreezableDependencyList

fun CurseforgeOptions.threadDependencies() {
    threadDependencies.curseForgeIds().forEach(::optional)
}

fun ModrinthOptions.threadDependencies() {
    threadDependencies.modrinthIds().forEach(::optional)
}

val forgeDependencies = project(":forge").extra["mod_dependencies"] as FreezableDependencyList

publishMods {
    changelog = modChangelog.joinToString("\n")
    type = releaseType

    dryRun = providers.systemProperty("MOD_UPLOAD_DEBUG").orElse("false").map { it == "true" }

    curseforge("CurseForgeFabric") {
        from(commonCurseForgeOptions, fabricOptions)

        requires("fabric-api")
        threadDependencies()
    }

    curseforge("CurseForgeQuilt") {
        from(commonCurseForgeOptions, quiltOptions)

        requires("qsl")
        threadDependencies()
    }

    curseforge("CurseForgeForge") {
        from(commonCurseForgeOptions, forgeOptions)

        forgeDependencies.curseForgeIds().forEach(::optional)
    }

    modrinth("ModrinthFabric") {
        from(commonModrinthOptions, fabricOptions)

        requires("fabric-api")
        threadDependencies()
    }

    modrinth("ModrinthQuilt") {
        from(commonModrinthOptions, quiltOptions)

        requires("qsl")
        threadDependencies()
    }

    modrinth("ModrinthForge") {
        from(commonModrinthOptions, forgeOptions)

        forgeDependencies.modrinthIds().forEach(::optional)
    }
}

tasks.getByName("publishModrinthFabric").mustRunAfter("publishModrinthForge", "publishModrinthQuilt")
tasks.getByName("publishModrinthForge").mustRunAfter("publishModrinthQuilt")

tasks.getByName("publishCurseForgeFabric").mustRunAfter("publishCurseForgeForge", "publishCurseForgeQuilt")
tasks.getByName("publishCurseForgeForge").mustRunAfter("publishCurseForgeQuilt")

private fun getGitCommit(): String {
    return ProcessGroovyMethods.getText(ProcessGroovyMethods.execute("git rev-parse HEAD"))
}
