package semele.quinn.stowage.impl

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import semele.quinn.stowage.impl.item.StorageMutator

import net.minecraft.world.item.Item.Properties as ItemProperties

class CoreStowagePlugin : StowageLoadingPlugin {
    private lateinit var storageMutator: Item

    override fun priority(): Int = 10

    override fun registerItems(registry: RegistryHelper<Item>) {
        storageMutator = registry.register(Utils.id("storage_mutator"), StorageMutator(ItemProperties()))
    }

    override fun getCreativeTabIcon(): ItemStack = ItemStack.EMPTY

    override fun getCreativeTabStacks(): List<ItemStack> {
        return listOf(
            storageMutator.defaultInstance
        )
    }
}