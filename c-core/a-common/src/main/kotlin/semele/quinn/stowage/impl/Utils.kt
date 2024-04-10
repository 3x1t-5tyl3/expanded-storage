package semele.quinn.stowage.impl

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import net.minecraft.world.level.block.state.BlockBehaviour.Properties as BlockProperties

object Utils {
    const val MOD_ID = "stowage"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    fun id(path: String) = ResourceLocation(MOD_ID, path)

    fun BlockProperties.flammable(): BlockProperties = ignitedByLava()
}