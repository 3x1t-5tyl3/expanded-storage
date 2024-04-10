package semele.quinn.stowage.impl

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader

class Main : ModInitializer {
    private lateinit var plugins: Map<String, StowageLoadingPlugin>

    override fun onInitialize() {
        Utils.LOGGER.info("Hello from Stowage. (Fabric/Quilt)")

        plugins = FabricLoader.getInstance().getEntrypointContainers("stowage:plugin", StowageLoadingPlugin::class.java).map {
            return@map Pair(it.provider.metadata.id, it.entrypoint)
        }.toMap()

        forEachPlugin {
            it.registerBlocks()
            it.registerBlockEntities()
            it.registerItems()
            it.registerStats()
        }
    }

    private fun forEachPlugin(function: (StowageLoadingPlugin) -> Unit) {
        plugins.values.forEach(function)
    }
}