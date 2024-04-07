rootProject.name = "stowage"

listOf("c-core", "d-barrels", "d-chests", "d-mini-blocks").forEach {
    include(":$it:a-common")
    include(":$it:b-neoforge")
    include(":$it:c-thread")
    include(":$it:d-fabric")
    include(":$it:d-quilt")
}