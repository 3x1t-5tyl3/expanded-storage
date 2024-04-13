package semele.quinn.stowage.impl

import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import semele.quinn.stowage.impl.item.MutatorData
import semele.quinn.stowage.impl.item.MutatorMode
import semele.quinn.stowage.impl.item.StorageMutator
import semele.quinn.stowage.impl.plugin.RegistryHelper
import semele.quinn.stowage.impl.plugin.StowageLoadingPlugin
import java.util.Optional

import net.minecraft.world.item.Item.Properties as ItemProperties

class CoreStowagePlugin : StowageLoadingPlugin {
    private lateinit var storageMutator: Item

    override fun priority(): Int = 10

    override fun registerItems(registry: RegistryHelper<Item>) {
        storageMutator = registry.register(Utils.id("mutator"),
            StorageMutator(ItemProperties()
                .component(MutatorData.COMPONENT, MutatorData(MutatorMode.MERGE, Optional.empty()))
                .stacksTo(1)
            )
        )
    }

    override fun registerDataComponents(registry: RegistryHelper<DataComponentType<*>>) {
        registry.register(Utils.id("mutator_data"), MutatorData.COMPONENT)
    }

    override fun getCreativeTabIcon(): ItemStack = ItemStack.EMPTY

    override fun getCreativeTabStacks(): List<ItemStack> {
        return listOf(
            storageMutator.defaultInstance
        )
    }
}