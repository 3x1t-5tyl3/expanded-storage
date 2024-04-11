package semele.quinn.stowage.impl

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import semele.quinn.stowage.impl.plugin.StowageLoadingPlugin

class Main : ModInitializer {
    private lateinit var plugins: Map<String, StowageLoadingPlugin>

    override fun onInitialize() {
        Utils.LOGGER.info("Hello from Stowage. (Fabric/Quilt)")

        plugins = FabricLoader.getInstance().getEntrypointContainers("stowage:plugin", StowageLoadingPlugin::class.java).map {
            return@map Pair(it.provider.metadata.id, it.entrypoint)
        }.sortedByDescending { it.second.priority() }.toMap()

        forEachPlugin {
            it.registerBlocks()
            it.registerBlockEntities()
            it.registerItems()
            it.registerStats()
        }

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Utils.id("tab"), FabricItemGroup
            .builder()
            .icon {
                plugins.values.first { !it.getCreativeTabIcon().isEmpty }.getCreativeTabIcon()
            }
            .displayItems { _, output ->
                forEachPlugin {
                    it.getCreativeTabStacks().forEach(output::accept)
                }
            }
            .title(Component.translatable("itemGroup.stowage.tab"))
            .build())
    }

    private fun forEachPlugin(function: (StowageLoadingPlugin) -> Unit) {
        plugins.values.forEach(function)
    }
}