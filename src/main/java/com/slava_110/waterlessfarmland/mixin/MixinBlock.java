package com.slava_110.waterlessfarmland.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
abstract class MixinBlock {

    @Inject(
            method = "onBlockActivated",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> ci) {
        //noinspection RedundantCast,DataFlowIssue
        if ((Block) ((Object) this) == Blocks.FARMLAND) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (!heldItem.isEmpty() && ArrayUtils.contains(OreDictionary.getOreIDs(heldItem), OreDictionary.getOreID("fertilizer"))) {
                worldIn.setBlockState(pos, state.withProperty(BlockFarmland.MOISTURE, 7));
                if(!playerIn.capabilities.isCreativeMode)
                    heldItem.shrink(1);
                ci.setReturnValue(true);
            }
        }
    }
}