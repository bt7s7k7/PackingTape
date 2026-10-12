package bt7s7k7.packing_tape;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = PackingTape.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = PackingTape.MODID, value = Dist.CLIENT)
public class PackingTapeClient {
	public PackingTapeClient(ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

	private static final ResourceLocation PACKED_ITEM_OVERLAY = ResourceLocation.fromNamespaceAndPath(PackingTape.MODID, "textures/gui/packed_item_overlay.png");

	@SubscribeEvent
	public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
		var dynamicOverlayDecorator = (IItemDecorator) (guiGraphics, font, stack, xOffset, yOffset) -> {
			if (!stack.has(PackingTape.IS_PACKED)) return false;

			guiGraphics.pose().pushPose();

			// Translate the Z-axis by 200 so the texture renders above the item model but below
			// tooltips. Vanilla durability bars use a similar offset.
			guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);

			guiGraphics.blit(PACKED_ITEM_OVERLAY, xOffset, yOffset, 0, 0, 16, 16, 16, 16);

			guiGraphics.pose().popPose();

			return false;
		};

		for (var item : BuiltInRegistries.ITEM) {
			// This should perhaps be a more conservative check, we only need to add this to items
			// that make block entities.
			if (!(item instanceof BlockItem)) continue;
			event.register(item, dynamicOverlayDecorator);
		}

	}
}
