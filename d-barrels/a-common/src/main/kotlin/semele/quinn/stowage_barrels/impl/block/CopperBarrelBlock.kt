package semele.quinn.stowage_barrels.impl.block

import net.minecraft.world.level.block.WeatheringCopper
import net.minecraft.world.level.block.WeatheringCopper.WeatherState

class CopperBarrelBlock(properties: Properties, private val state: WeatherState) : BarrelBlock(properties), WeatheringCopper {
    override fun getAge(): WeatherState {
        return state
    }
}