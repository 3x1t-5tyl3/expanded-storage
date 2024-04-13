package semele.quinn.stowage.impl

import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Items
import semele.quinn.stowage.impl.plugin.RegistryHelper
import semele.quinn.stowage.impl.plugin.StowageLoadingPlugin

class Main : ModInitializer {
    private lateinit var plugins: Map<String, StowageLoadingPlugin>

    override fun onInitialize() {
        Utils.LOGGER.info("Hello from Stowage. (Fabric/Quilt)")

        plugins = FabricLoader.getInstance().getEntrypointContainers("stowage:plugin", StowageLoadingPlugin::class.java).map {
            return@map Pair(it.provider.metadata.id, it.entrypoint)
        }.sortedByDescending { it.second.priority() }.toMap()

        BuiltInRegistries.REGISTRY.forEach { registry ->
            val helper = RegistryHelper(registry)
            forEachPlugin { it.register(helper) }
        }

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Utils.id("tab"), FabricItemGroup
            .builder()
            .icon {
                plugins.values.firstOrNull { !it.getCreativeTabIcon().isEmpty }?.getCreativeTabIcon() ?: Items.CHEST.defaultInstance
            }
            .displayItems { _, output ->
                forEachPlugin {
                    it.getCreativeTabStacks().forEach(output::accept)
                }
            }
            .title(Component.translatable("stowage.tab"))
            .build()
        )

        forEachPlugin(StowageLoadingPlugin::initialise)
        if (FabricLoader.getInstance().environmentType == EnvType.CLIENT) {
            forEachPlugin(StowageLoadingPlugin::initialiseClient)
        }
    }

    private fun forEachPlugin(function: (StowageLoadingPlugin) -> Unit) {
        plugins.values.forEach(function)
    }
}