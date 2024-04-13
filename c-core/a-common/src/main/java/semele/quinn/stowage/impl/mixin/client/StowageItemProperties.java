package semele.quinn.stowage.impl.mixin.client;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import semele.quinn.stowage.impl.Utils;
import semele.quinn.stowage.impl.item.MutatorData;

@Mixin(ItemProperties.class)
public abstract class StowageItemProperties {
    @Shadow
    private static void register(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
    }

    @Inject(
            method = "<clinit>",
            at = @At("TAIL"),
            remap = false
    )
    private static void addItemProperties(CallbackInfo ci) {
        register(BuiltInRegistries.ITEM.get(Utils.INSTANCE.id("mutator")), Utils.INSTANCE.id("mutator_mode"), (stack, level, entity, seed) -> {
            MutatorData data = stack.get(MutatorData.Companion.getCOMPONENT());

            return switch (data.getMode()) {
                case MERGE -> 0.25f;
                case SPLIT -> 0.50f;
                case ROTATE -> 0.75f;
                case TAILOR -> 1.00f;
            };
        });
    }
}
