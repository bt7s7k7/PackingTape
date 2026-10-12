package bt7s7k7.packing_tape.items;

import bt7s7k7.packing_tape.PackedItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public class PackingTapeItem extends Item {
	public PackingTapeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		do {
			if (!(context.getLevel() instanceof ServerLevel level)) break;
			if (!(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof BlockEntity blockEntity)) break;

			// Create item from block like in Minecraft.pickBlock()
			var state = level.getBlockState(context.getClickedPos());
			var hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
			var stack = state.getCloneItemStack(hitResult, level, hitResult.getBlockPos(), context.getPlayer());

			// Add data to item like in Minecraft.addCustomNbtData()
			var tag = blockEntity.saveCustomAndMetadata(level.registryAccess());
			BlockItem.setBlockEntityData(stack, blockEntity.getType(), tag);
			stack.applyComponents(blockEntity.collectComponents());

			PackedItemUtil.markItemStack(stack);
			PackedItemUtil.addDataDescription(stack);

			// Remove the packed up block from level
			Clearable.tryClear(blockEntity);
			level.setBlock(context.getClickedPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS);

			// Drop the packed item
			Block.popResource(level, context.getClickedPos(), stack);

			// Use up the tape -- creative mode is handled by ItemStack.consume
			context.getItemInHand().consume(1, context.getPlayer());

			return InteractionResult.CONSUME;
		} while (false);

		return super.useOn(context);

	}
}
