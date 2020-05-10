package com.fusion.fusionsbookslib.block.tile;

import com.fusion.fusionsbookslib.FusionsBooksLib;
import com.fusion.fusionsbookslib.init.BlockInit;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, FusionsBooksLib.MODID);

    public static final RegistryObject<TileEntityType<AltarTileEntity>> ALTAR =
            TILE_ENTITY_TYPES.register("altar", () -> TileEntityType.Builder.create(AltarTileEntity::new, BlockInit.ALTAR.get()).build(null));
}
