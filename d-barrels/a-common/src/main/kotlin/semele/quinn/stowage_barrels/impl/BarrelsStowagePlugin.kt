package semele.quinn.stowage_barrels.impl

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.WeatheringCopper.WeatherState
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import semele.quinn.stowage.impl.RegistryHelper
import semele.quinn.stowage.impl.StowageLoadingPlugin
import semele.quinn.stowage.impl.Utils
import semele.quinn.stowage.impl.Utils.flammable
import semele.quinn.stowage_barrels.impl.block.BarrelBlock
import semele.quinn.stowage_barrels.impl.block.BarrelBlockEntity
import semele.quinn.stowage_barrels.impl.block.CopperBarrelBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties as BlockProperties
import net.minecraft.world.item.Item.Properties as ItemProperties

open class BarrelsStowagePlugin : StowageLoadingPlugin {
    private lateinit var woodenBarrel: Block

    private lateinit var copperBarrel: Block
    private lateinit var exposedCopperBarrel: Block
    private lateinit var weatheredCopperBarrel: Block
    private lateinit var oxidizedCopperBarrel: Block

    private lateinit var waxedCopperBarrel: Block
    private lateinit var waxedExposedCopperBarrel: Block
    private lateinit var waxedWeatheredCopperBarrel: Block
    private lateinit var waxedOxidizedCopperBarrel: Block

    private lateinit var ironBarrel: Block
    private lateinit var goldenBarrel: Block
    private lateinit var diamondBarrel: Block
    private lateinit var obsidianBarrel: Block
    private lateinit var netheriteBarrel: Block

    override fun registerBlocks(registry: RegistryHelper<Block>) {
        val softMetalProperties = BlockProperties.of().barrel().flammable().strength(3f, 6f)
        val strongMetalProperties = BlockProperties.of().barrel().flammable().strength(5f, 6f)
        val obsidianProperties = BlockProperties.of().barrel().flammable().strength(50f, 1200f)
        val netheriteProperties = BlockProperties.of().barrel().strength(50f, 1200f)

        woodenBarrel = registry.register("wooden_barrel", BarrelBlock(BlockProperties.ofFullCopy(Blocks.BARREL)))

        copperBarrel = registry.register("copper_barrel", CopperBarrelBlock(softMetalProperties, WeatherState.UNAFFECTED))
        exposedCopperBarrel = registry.register("exposed_copper_barrel", CopperBarrelBlock(softMetalProperties, WeatherState.EXPOSED))
        weatheredCopperBarrel = registry.register("weathered_copper_barrel", CopperBarrelBlock(softMetalProperties, WeatherState.WEATHERED))
        oxidizedCopperBarrel = registry.register("oxidized_copper_barrel", CopperBarrelBlock(softMetalProperties, WeatherState.OXIDIZED))

        waxedCopperBarrel = registry.register("waxed_copper_barrel", BarrelBlock(softMetalProperties))
        waxedExposedCopperBarrel = registry.register("waxed_exposed_copper_barrel", BarrelBlock(softMetalProperties))
        waxedWeatheredCopperBarrel = registry.register("waxed_weathered_copper_barrel", BarrelBlock(softMetalProperties))
        waxedOxidizedCopperBarrel = registry.register("waxed_oxidized_copper_barrel", BarrelBlock(softMetalProperties))

        ironBarrel = registry.register("iron_barrel", BarrelBlock(strongMetalProperties))
        goldenBarrel = registry.register("golden_barrel", BarrelBlock(softMetalProperties))
        diamondBarrel = registry.register("diamond_barrel", BarrelBlock(strongMetalProperties))
        obsidianBarrel = registry.register("obsidian_barrel", BarrelBlock(obsidianProperties))
        netheriteBarrel = registry.register("netherite_barrel", BarrelBlock(netheriteProperties))
    }

    override fun registerItems(registry: RegistryHelper<Item>) {
        registry.register(woodenBarrel, BlockItem(woodenBarrel, ItemProperties()))

        registry.register(copperBarrel, BlockItem(copperBarrel, ItemProperties()))
        registry.register(exposedCopperBarrel, BlockItem(exposedCopperBarrel, ItemProperties()))
        registry.register(weatheredCopperBarrel, BlockItem(weatheredCopperBarrel, ItemProperties()))
        registry.register(oxidizedCopperBarrel, BlockItem(oxidizedCopperBarrel, ItemProperties()))

        registry.register(waxedCopperBarrel, BlockItem(waxedCopperBarrel, ItemProperties()))
        registry.register(waxedExposedCopperBarrel, BlockItem(waxedExposedCopperBarrel, ItemProperties()))
        registry.register(waxedWeatheredCopperBarrel, BlockItem(waxedWeatheredCopperBarrel, ItemProperties()))
        registry.register(waxedOxidizedCopperBarrel, BlockItem(waxedOxidizedCopperBarrel, ItemProperties()))

        registry.register(ironBarrel, BlockItem(ironBarrel, ItemProperties()))
        registry.register(goldenBarrel, BlockItem(goldenBarrel, ItemProperties()))
        registry.register(diamondBarrel, BlockItem(diamondBarrel, ItemProperties()))
        registry.register(obsidianBarrel, BlockItem(obsidianBarrel, ItemProperties()))
        registry.register(netheriteBarrel, BlockItem(netheriteBarrel, ItemProperties().fireResistant()))
    }

    override fun registerBlockEntities(registry: RegistryHelper<BlockEntityType<*>>) {
        registry.register("barrel", BlockEntityType.Builder.of(::BarrelBlockEntity,
            woodenBarrel,
            copperBarrel, exposedCopperBarrel, weatheredCopperBarrel, oxidizedCopperBarrel,
            waxedCopperBarrel, waxedExposedCopperBarrel, waxedWeatheredCopperBarrel, waxedOxidizedCopperBarrel,
            ironBarrel, goldenBarrel, diamondBarrel, obsidianBarrel, netheriteBarrel
        ).build(null))
    }

    override fun registerStats(helper: (ResourceLocation) -> ResourceLocation) {
        helper.invoke(Utils.id("open_wood_barrel"))
        helper.invoke(Utils.id("open_copper_barrel"))
        helper.invoke(Utils.id("open_iron_barrel"))
        helper.invoke(Utils.id("open_gold_barrel"))
        helper.invoke(Utils.id("open_diamond_barrel"))
        helper.invoke(Utils.id("open_obsidian_barrel"))
        helper.invoke(Utils.id("open_netherite_barrel"))
    }

    override fun getCreativeTabIcon(): ItemStack = diamondBarrel.asItem().defaultInstance

    override fun getCreativeTabStacks(): List<ItemStack> {
        return listOf(
            woodenBarrel.asItem().defaultInstance,

            copperBarrel.asItem().defaultInstance,
            exposedCopperBarrel.asItem().defaultInstance,
            weatheredCopperBarrel.asItem().defaultInstance,
            oxidizedCopperBarrel.asItem().defaultInstance,

            waxedCopperBarrel.asItem().defaultInstance,
            waxedExposedCopperBarrel.asItem().defaultInstance,
            waxedWeatheredCopperBarrel.asItem().defaultInstance,
            waxedOxidizedCopperBarrel.asItem().defaultInstance,

            ironBarrel.asItem().defaultInstance,
            goldenBarrel.asItem().defaultInstance,
            diamondBarrel.asItem().defaultInstance,
            obsidianBarrel.asItem().defaultInstance,
            netheriteBarrel.asItem().defaultInstance
        )
    }

    private fun BlockProperties.barrel(): BlockProperties {
        mapColor(MapColor.WOOD)
        instrument(NoteBlockInstrument.BASS)
        sound(SoundType.WOOD)

        return this
    }
}