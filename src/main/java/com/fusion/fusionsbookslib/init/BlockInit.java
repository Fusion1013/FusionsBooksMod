package com.fusion.fusionsbookslib.init;

import com.fusion.fusionsbookslib.FusionsBooksLib;
import com.fusion.fusionsbookslib.block.BlockAltar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
            FusionsBooksLib.MODID);

    public static final RegistryObject<Block> ALTAR = BLOCKS.register("altar",
            () -> new BlockAltar(Block.Properties.create(Material.IRON)));
}
