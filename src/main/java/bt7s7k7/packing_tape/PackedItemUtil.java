package bt7s7k7.packing_tape;

import java.util.ArrayList;

import bt7s7k7.packing_tape.components.IsPacked;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemLore;

public final class PackedItemUtil {
	private PackedItemUtil() {}

	public static void decorateItemStack(ItemStack stack) {
		PackingTape.LOGGER.info("Hello");

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
}
