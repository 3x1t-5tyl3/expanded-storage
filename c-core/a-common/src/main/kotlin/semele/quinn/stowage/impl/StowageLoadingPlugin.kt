package semele.quinn.stowage.impl

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType

interface StowageLoadingPlugin {
    fun registerBlocks(registry: RegistryHelper<Block> = RegistryHelper(BuiltInRegistries.BLOCK))
    fun registerItems(registry: RegistryHelper<Item> = RegistryHelper(BuiltInRegistries.ITEM))
    fun registerBlockEntities(registry: RegistryHelper<BlockEntityType<*>> = RegistryHelper(BuiltInRegistries.BLOCK_ENTITY_TYPE))
    fun registerStats(helper: (ResourceLocation) -> ResourceLocation = {
        Registry.register(BuiltInRegistries.CUSTOM_STAT, it, it)
    })
}