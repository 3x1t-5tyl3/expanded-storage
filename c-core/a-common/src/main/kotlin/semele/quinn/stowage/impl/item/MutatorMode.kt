package semele.quinn.stowage.impl.item

import com.mojang.serialization.Codec
import net.minecraft.util.StringRepresentable

enum class MutatorMode : StringRepresentable {
    MERGE,
    SPLIT,
    ROTATE,
    TAILOR;

    override fun getSerializedName(): String = when(this) {
        MERGE -> "merge"
        SPLIT -> "split"
        ROTATE -> "rotate"
        TAILOR -> "tailor"
    }

    fun next(): MutatorMode = when(this) {
        MERGE -> SPLIT
        SPLIT -> ROTATE
        ROTATE -> TAILOR
        TAILOR -> MERGE
    }

    companion object {
        val CODEC: Codec<MutatorMode> = StringRepresentable.fromValues(MutatorMode::values)
    }
}