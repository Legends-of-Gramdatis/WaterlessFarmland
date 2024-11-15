package com.slava_110.waterlessfarmland;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "waterlessfarmland")
public class WaterlessFarmlandMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register bone meal as "fertilizer" in the ore dictionary
        OreDictionary.registerOre("fertilizer", new ItemStack(Items.DYE, 1, 15)); // Bone meal has metadata 15
    }
}