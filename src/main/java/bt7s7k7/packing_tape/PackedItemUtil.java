package bt7s7k7.packing_tape;

import java.util.ArrayList;

import bt7s7k7.packing_tape.components.IsPacked;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;

public final class PackedItemUtil {
	private PackedItemUtil() {}

	public static void markItemStack(ItemStack stack) {
		stack.applyComponents(DataComponentPatch.builder()
				.set(PackingTape.IS_PACKED.get(), IsPacked.INSTANCE)
				.build());
	}

	public static void addDataDescription(ItemStack stack) {
		if (!stack.has(PackingTape.IS_PACKED)) return;

		var description = new ArrayList<Component>();
		var style = Style.EMPTY.applyFormat(ChatFormatting.GOLD).withItalic(false);

		description.add(Component.translatable("gui.packing_tape.is_packed").withStyle(style));

		if (stack.get(DataComponents.CONTAINER) instanceof ItemContainerContents contents) {
			var itemCount = contents.nonEmptyStream().count();
			description.add(Component.translatable("gui.packing_tape.item_count", itemCount).withStyle(style));
		}

		stack.set(DataComponents.LORE, new ItemLore(description));
	}

	public static ItemStack removeDescriptionBeforeLoad(ItemStack stack) {
		if (!stack.has(PackingTape.IS_PACKED)) return stack;

		stack = stack.copy();

		stack.applyComponents(DataComponentPatch.builder()
				.remove(PackingTape.IS_PACKED.get())
				.remove(DataComponents.LORE)
				.build());

		return stack;
	}

	public static boolean tryApplyOpDataInSurvival(Level level, Player player, BlockPos pos, ItemStack stack) {
		// This function handles placing packed OP blocks, like spawners, which would normally have
		// their data discarded if the placing player wasn't both an admin and in creative

		if (!stack.has(PackingTape.IS_PACKED)) return false;

		// Minecraft only does the OP check on the server so we only handle it on the server
		if (!(level instanceof ServerLevel)) return false;

		var data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
		if (data.isEmpty()) return false;

		var blockEntity = level.getBlockEntity(pos);
		if (blockEntity == null) return false;

		if (!blockEntity.onlyOpCanSetNbt() || player != null && player.canUseGameMasterBlocks()) return false;

		data.loadInto(blockEntity, level.registryAccess());
		return true;
	}
}
