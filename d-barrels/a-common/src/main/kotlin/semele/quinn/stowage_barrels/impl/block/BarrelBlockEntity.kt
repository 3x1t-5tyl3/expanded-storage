package semele.quinn.stowage_barrels.impl.block

import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import semele.quinn.stowage.impl.Utils

class BarrelBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state) {
    companion object {
        private val BLOCK_ENTITY_TYPE: BlockEntityType<BarrelBlockEntity> = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(Utils.id("barrel")) as BlockEntityType<BarrelBlockEntity>
    }

    constructor(pos: BlockPos, state: BlockState) : this(BLOCK_ENTITY_TYPE, pos, state)
}