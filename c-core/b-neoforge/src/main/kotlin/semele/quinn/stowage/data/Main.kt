package semele.quinn.stowage.data

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.data.event.GatherDataEvent
import semele.quinn.stowage.data.providers.RecipeProvider


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object Main {
    @JvmStatic
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val output = generator.packOutput
        val lookupProvider = event.lookupProvider
        val fileHelper = event.existingFileHelper

        generator.addProvider(event.includeServer(), RecipeProvider(output, lookupProvider))
    }
}