package bt7s7k7.packing_tape;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import bt7s7k7.packing_tape.components.IsPacked;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(PackingTape.MODID)
public class PackingTape {
	public static final String MODID = "packing_tape";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<IsPacked>> IS_PACKED = COMPONENTS.registerComponentType("is_packed", v -> v
			.persistent(IsPacked.CODEC)
			.networkSynchronized(IsPacked.STREAM_CODEC));

	public static final DeferredItem<Item> PACKING_TAPE = ITEMS.registerSimpleItem("packing_tape", new Item.Properties());

	public PackingTape(IEventBus modEventBus, ModContainer modContainer) {
		ITEMS.register(modEventBus);
		COMPONENTS.register(modEventBus);

		modEventBus.addListener(this::addCreative);
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(PACKING_TAPE);
		}
	}
}
