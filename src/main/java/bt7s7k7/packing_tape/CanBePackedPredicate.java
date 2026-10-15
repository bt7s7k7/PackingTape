package bt7s7k7.packing_tape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class CanBePackedPredicate {
	private Set<ResourceLocation> whitelist = new HashSet<>();
	private Set<ResourceLocation> blacklist = new HashSet<>();

	private List<Pattern> whitelistPatterns = new ArrayList<>();
	private List<Pattern> blacklistPatterns = new ArrayList<>();

	public CanBePackedPredicate() {
		initializeList(Config.WHITELIST.get(), this.whitelist, this.whitelistPatterns);
		initializeList(Config.BLACKLIST.get(), this.blacklist, this.blacklistPatterns);
	}

	public boolean test(ItemStack stack, BlockEntity blockEntity) {
		var id = BuiltInRegistries.ITEM.getKey(stack.getItem());
		var idString = id.toString();

		if (this.whitelist.contains(id)) return true;
		if (this.whitelistPatterns.stream().anyMatch(v -> v.matcher(idString).matches())) return true;

		if (this.blacklist.contains(id)) return false;
		if (this.blacklistPatterns.stream().anyMatch(v -> v.matcher(idString).matches())) return false;

		return blockEntity == null ? Config.BLOCKS_BY_DEFAULT.getAsBoolean() : Config.BLOCK_ENTITIES_BY_DEFAULT.getAsBoolean();
	}

	private static void initializeList(List<? extends String> ids, Set<ResourceLocation> constants, List<Pattern> patterns) {
		for (var id : ids) {
			if (id.contains("*")) {
				patterns.add(Pattern.compile("^" + Pattern.quote(id).replace("*", "\\E.*\\Q") + "$"));
			} else {
				try {
					constants.add(ResourceLocation.parse(id));
				} catch (ResourceLocationException exception) {
					throw new RuntimeException("Failed to parse block ID for filtering: " + id);
				}
			}
		}
	}

	private static CanBePackedPredicate _instance = null;

	public static CanBePackedPredicate instance() {
		if (_instance != null) return _instance;
		return _instance = new CanBePackedPredicate();
	}
}
