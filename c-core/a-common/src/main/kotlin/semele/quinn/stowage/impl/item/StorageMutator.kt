package semele.quinn.stowage.impl.item

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import semele.quinn.stowage.impl.Utils
import java.util.Optional

class StorageMutator(properties: Properties) : Item(properties) {
    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        text: MutableList<Component>,
        flag: TooltipFlag
    ) {
        val mode = stack.get(MutatorData.COMPONENT)!!.mode

        text.add(Component.translatable("stowage.item.mutator.mode", Component.translatable("stowage.item.mutator.${mode.serializedName}")).withStyle(ChatFormatting.GRAY))
        text.add(Component.empty())
        text.add(Component.translatable("stowage.item.mutator.change_mode", USE).withStyle(ChatFormatting.GRAY))
        text.add(Component.translatable("stowage.item.mutator.description_${mode.serializedName}", USE).withStyle(ChatFormatting.GRAY))
        if (mode == MutatorMode.ROTATE) {
            text.add(Component.translatable("stowage.item.mutator.description_${mode.serializedName}_alt", ALT_USE).withStyle(ChatFormatting.GRAY))
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        val nextMode = stack.get(MutatorData.COMPONENT)!!.mode.next()

        stack.set(MutatorData.COMPONENT, MutatorData(nextMode, Optional.empty()))
        player.cooldowns.addCooldown(this, Utils.SHORT_DELAY)

        if (!level.isClientSide()) {
            player.displayClientMessage(Component.translatable("stowage.item.mutator.description_${nextMode.serializedName}", USE), true);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide())
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        return InteractionResult.FAIL
    }

    companion object {
        val USE: Component = Component.translatable("stowage.item.use").withStyle(ChatFormatting.GOLD)
        val ALT_USE: Component = Component.translatable(
            "stowage.item.alt_use",
            Component.translatable("key.sneak").withStyle(ChatFormatting.GOLD),
            Component.translatable("stowage.item.use").withStyle(ChatFormatting.GOLD)
        )
    }
}