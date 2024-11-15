package com.slava_110.waterlessfarmland.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
// import net.minecraft.block.state.IBlockState;
// import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.init.Blocks;
// import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
// import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
// import net.minecraftforge.oredict.OreDictionary;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFarmland.class)
abstract class MixinBlockFarmland extends Block {

    private MixinBlockFarmland(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean getTickRandomly() {
        return false;
    }

    @Inject(
            method = "onFallenUpon",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "hasWater",
            at = @At("HEAD")
    )
    public void hasWater(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(true);
    }

    // @Inject(
    //         method = "onBlockActivated",
    //         at = @At("HEAD"),
    //         cancellable = true
    // )
    // public void onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, CallbackInfoReturnable<Boolean> ci) {
    //     ItemStack heldItem = playerIn.getHeldItem(hand);
    //     if (!heldItem.isEmpty() && OreDictionary.containsMatch(false, OreDictionary.getOres("fertilizer"), heldItem)) {
    //         worldIn.setBlockState(pos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7));
    //         heldItem.shrink(1);
    //         ci.setReturnValue(true);
    //     }
    // }
}
