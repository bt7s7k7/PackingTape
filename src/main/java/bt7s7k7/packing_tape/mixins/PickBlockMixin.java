package bt7s7k7.packing_tape.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bt7s7k7.packing_tape.PackedItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(Minecraft.class)
public class PickBlockMixin {
	@Inject(method = "addCustomNbtData", at = @At("RETURN"), remap = false)
	private void packingTape$onAddCustomNbtData(ItemStack stack, BlockEntity blockEntity, RegistryAccess registryAccess, CallbackInfo ci) {
		PackedItemUtil.markItemStack(stack);
	}
}
