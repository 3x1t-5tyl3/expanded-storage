package semele.quinn.stowage.impl.plugin

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import semele.quinn.stowage.impl.Utils

class RegistryHelper<T>(private val registry: Registry<T>) {
    fun register(id: Any, value: T & Any): T {
        val name = when (id) {
            is Block -> id.builtInRegistryHolder().key().location()
            is ResourceLocation -> id
            is String -> Utils.id(id)
            else -> throw IllegalArgumentException("id must be one of [Block, ResourceLocation, String]")
        }

        return Registry.register(registry, name, value)
    }

    fun register(value: T & Any): T {
        return when(value) {
            is BlockItem -> register(value.block, value)
            else -> throw IllegalArgumentException("value must be one of [BlockItem]")
        }
    }
}