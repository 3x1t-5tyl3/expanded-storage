package semele.quinn.stowage_barrels.impl.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN

open class BarrelBlock(properties: Properties) : BaseEntityBlock(properties) {
    companion object {
        private val CODEC = simpleCodec(::BarrelBlock)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        if (context.player == null) {
            return defaultBlockState().setValue(FACING, Direction.UP)
        }

        return defaultBlockState().setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, OPEN)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> = CODEC

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BarrelBlockEntity = BarrelBlockEntity(pos, state)

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL
}