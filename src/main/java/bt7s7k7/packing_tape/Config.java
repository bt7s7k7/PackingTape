package bt7s7k7.packing_tape;

import java.util.List;

import com.google.common.base.Predicates;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue BLOCKS_BY_DEFAULT = BUILDER
			.comment("Whether to allow packing all blocks by default. This can be further refined using the blacklist.")
			.define("blocks_by_default", false);

	public static final ModConfigSpec.BooleanValue BLOCK_ENTITIES_BY_DEFAULT = BUILDER
			.comment("Whether to allow packing all block entities by default. This can be further refined using the blacklist.")
			.define("block_entities_by_default", true);

	public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST = BUILDER
			.comment("A list of block IDs that cannot be packed. You can use the character '*' as a wildcard.")
			.defineListAllowEmpty("blacklist", List.of(
					"minecraft:*command_block",
					"minecraft:bedrock",
					"minecraft:barrier",
					"minecraft:structure_void",
					"minecraft:structure_block",
					"minecraft:jigsaw",
					"minecraft:light",
					"minecraft:end_portal_frame"), () -> "", Predicates.alwaysTrue());

	public static final ModConfigSpec.ConfigValue<List<? extends String>> WHITELIST = BUILDER
			.comment("A list of block IDs that can be packed. You can use the character '*' as a wildcard. This list has precedence over the blacklist.")
			.defineListAllowEmpty("whitelist", List.of(
					"minecraft:budding_amethyst"), () -> "", Predicates.alwaysTrue());

	static final ModConfigSpec SPEC = BUILDER.build();
}
