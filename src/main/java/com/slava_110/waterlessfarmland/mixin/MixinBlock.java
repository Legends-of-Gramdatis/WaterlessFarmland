package com.slava_110.waterlessfarmland.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(
            method = "onBlockActivated",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, CallbackInfoReturnable<Boolean> ci) {
        Block block = state.getBlock();
        if (block == Blocks.FARMLAND) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (!heldItem.isEmpty() && OreDictionary.containsMatch(false, OreDictionary.getOres("fertilizer"), heldItem)) {
                worldIn.setBlockState(pos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7));
                heldItem.shrink(1);
                ci.setReturnValue(true);
            }
        }
    }
}