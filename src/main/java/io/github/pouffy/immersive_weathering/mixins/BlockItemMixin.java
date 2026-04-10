package io.github.pouffy.immersive_weathering.mixins;

import io.github.pouffy.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getPlacementState(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true)
    private void registerBlock(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (this.getBlock() == Blocks.HANGING_ROOTS) {
            BlockState blockstate = ModBlocks.HANGING_ROOTS_WALL.get().getStateForPlacement(context);
            BlockState state = null;
            LevelReader levelreader = context.getLevel();
            BlockPos blockpos = context.getClickedPos();

            for (Direction direction : context.getNearestLookingDirections()) {
                if (direction != Direction.DOWN) {
                    BlockState blockstate2 = direction == Direction.UP ? this.getBlock().getStateForPlacement(context) : blockstate;
                    if (blockstate2 != null && blockstate2.canSurvive(levelreader, blockpos)) {
                        state = blockstate2;
                        break;
                    }
                }
            }
            if (state != null && levelreader.isUnobstructed(state, blockpos, CollisionContext.empty())) {
                cir.setReturnValue(state);
            } else cir.setReturnValue(null);
        }
    }

    //@Inject(method = "registerBlocks(Ljava/util/Map;Lnet/minecraft/world/item/Item;)V", at = @At("TAIL"))
    //private void registerBlocks(Map<Block, Item> blockToItemMap, Item item, CallbackInfo ci) {
    //    if (this.getBlock() == Blocks.HANGING_ROOTS) {
    //        blockToItemMap.put(ModBlocks.HANGING_ROOTS_WALL.get(), item);
    //    }
    //}
}
