package semele.quinn.stowage.impl.plugin

import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType

interface StowageLoadingPlugin {
    fun priority(): Int = 0

    fun initialise() = Unit
    fun initialiseClient() = Unit

    fun register(registry: RegistryHelper<out Any>) {
        when {
            registry.key() == Registries.BLOCK -> registerBlocks(registry.cast())
            registry.key() == Registries.ITEM -> registerItems(registry.cast())
            registry.key() == Registries.BLOCK_ENTITY_TYPE -> registerBlockEntities(registry.cast())
            registry.key() == Registries.CUSTOM_STAT -> registerStats(registry.cast())
            registry.key() == Registries.DATA_COMPONENT_TYPE -> registerDataComponents(registry.cast())
        }
    }

    fun registerBlocks(registry: RegistryHelper<Block>) = Unit
    fun registerItems(registry: RegistryHelper<Item>) = Unit
    fun registerBlockEntities(registry: RegistryHelper<BlockEntityType<*>>) = Unit
    fun registerStats(registry: RegistryHelper<ResourceLocation>) = Unit
    fun registerDataComponents(registry: RegistryHelper<DataComponentType<*>>) = Unit

    fun getCreativeTabIcon(): ItemStack = ItemStack.EMPTY
    fun getCreativeTabStacks(): List<ItemStack> = listOf()
}