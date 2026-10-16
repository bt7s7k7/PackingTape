package bt7s7k7.packing_tape.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bt7s7k7.packing_tape.PackedItemUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
	@Inject(method = "saveToItem", at = @At("RETURN"), remap = false)
	public void packingTape$onSaveToItem(ItemStack stack, HolderLookup.Provider registries, CallbackInfo ci) {
		PackedItemUtil.addDataDescription(stack);
	}

	@ModifyVariable(method = "applyComponentsFromItemStack", at = @At("HEAD"), ordinal = 0, remap = false)
	public ItemStack packingTape$applyComponentsFromItemStack(ItemStack stack) {
		return PackedItemUtil.removeDescriptionBeforeLoad(stack);
	}
}
