package semele.quinn.stowage.impl

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent
import net.neoforged.neoforge.registries.RegisterEvent
import semele.quinn.stowage.impl.plugin.StowageLoadingPlugin
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
        val plugins = mutableListOf<Pair<String, StowageLoadingPlugin>>()

        ModList.get().sortedMods.forEach {
            val modProperties = it.modInfo.modProperties

            if ("stowagePluginClass" in modProperties) {
                val pluginClass = modProperties["stowagePluginClass"] as String

                try {
                    plugins.add(Pair(it.modId, Class.forName(pluginClass).getConstructor().newInstance() as StowageLoadingPlugin))
                } catch (e: Exception) {
                    Utils.LOGGER.error("Failed to find or load plugin class: $pluginClass")
                    throw e
                }
            }
        }

        this.plugins = Collections.unmodifiableMap(plugins.sortedByDescending { it.second.priority() }.toMap())
    }

    private fun registerContent(event: RegisterEvent) {
        when (event.registry) {
            BuiltInRegistries.CUSTOM_STAT -> forEachPlugin { it.registerStats() }
            BuiltInRegistries.BLOCK -> forEachPlugin { it.registerBlocks() }
            BuiltInRegistries.ITEM -> forEachPlugin { it.registerItems() }
            BuiltInRegistries.BLOCK_ENTITY_TYPE -> forEachPlugin { it.registerBlockEntities() }
            BuiltInRegistries.CREATIVE_MODE_TAB -> {
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Utils.id("tab"), CreativeModeTab
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
                    .build()
                )
            }
        }
    }

    private fun forEachPlugin(function: (StowageLoadingPlugin) -> Unit) {
        plugins.values.forEach(function)
    }
}