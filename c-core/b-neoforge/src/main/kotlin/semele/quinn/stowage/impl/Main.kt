package semele.quinn.stowage.impl

import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent
import net.neoforged.fml.javafmlmod.FMLModContainer
import net.neoforged.neoforge.registries.RegisterEvent
import java.util.*

@Mod(Utils.MOD_ID)
class Main(val container: ModContainer, val bus: IEventBus) {
    private lateinit var plugins: Map<String, StowageLoadingPlugin>

    init {
        Utils.LOGGER.info("Hello from Stowage. (NeoForge)")

        bus.addListener(this::findStowagePlugins)
        bus.addListener(this::registerContent)
    }

    private fun findStowagePlugins(event: FMLConstructModEvent) {
        val plugins = mutableMapOf<String, StowageLoadingPlugin>()

        ModList.get().sortedMods.forEach {
            val modProperties = it.modInfo.modProperties

            if ("stowagePluginClass" in modProperties) {
                val pluginClass = modProperties["stowagePluginClass"] as String

                try {
                    plugins[it.modId] = Class.forName(pluginClass).getConstructor().newInstance() as StowageLoadingPlugin
                } catch (e: Exception) {
                    Utils.LOGGER.error("Failed to find or load plugin class: $pluginClass")
                    throw e
                }
            }
        }

        this.plugins = Collections.unmodifiableMap(plugins)
    }

    private fun registerContent(event: RegisterEvent) {
        when (event.registry) {
            BuiltInRegistries.CUSTOM_STAT -> forEachPlugin { it.registerStats() }
            BuiltInRegistries.BLOCK -> forEachPlugin { it.registerBlocks() }
            BuiltInRegistries.ITEM -> forEachPlugin { it.registerItems() }
            BuiltInRegistries.BLOCK_ENTITY_TYPE -> forEachPlugin { it.registerBlockEntities() }
        }
    }

    private fun forEachPlugin(function: (StowageLoadingPlugin) -> Unit) {
        plugins.values.forEach(function)
    }
}