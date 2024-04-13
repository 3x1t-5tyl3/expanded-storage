package semele.quinn.stowage.impl.item

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import java.util.Optional

data class MutatorData(val mode: MutatorMode, val pos: Optional<BlockPos>) {
    companion object {
        private val CODEC: MapCodec<MutatorData> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                MutatorMode.CODEC.fieldOf("mode").forGetter(MutatorData::mode),
                BlockPos.CODEC.optionalFieldOf("pos").forGetter(MutatorData::pos)
            ).apply(instance, ::MutatorData)
        }

        private val STREAM_CODEC = StreamCodec.of(MutatorData::encode, MutatorData::decode)

        val COMPONENT: DataComponentType<MutatorData> = DataComponentType.builder<MutatorData>()
            .persistent(CODEC.codec())
            .networkSynchronized(STREAM_CODEC)
            .build()

        private fun encode(buffer: FriendlyByteBuf, data: MutatorData) {
            buffer.writeEnum(data.mode)
            buffer.writeOptional(data.pos, BlockPos.STREAM_CODEC)
        }

        private fun decode(buffer: FriendlyByteBuf): MutatorData {
            val mode = buffer.readEnum(MutatorMode::class.java)
            val pos = buffer.readOptional(BlockPos.STREAM_CODEC)
            return MutatorData(mode, pos)
        }
    }


}
