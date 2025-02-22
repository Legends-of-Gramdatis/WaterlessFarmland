package com.slava_110.waterlessfarmland.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
            at = @At("HEAD"),
            cancellable = true
    )
    public void hasWater(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(true);
    }
}
