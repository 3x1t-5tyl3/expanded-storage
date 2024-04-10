package semele.quinn.stowage.impl

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

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
}