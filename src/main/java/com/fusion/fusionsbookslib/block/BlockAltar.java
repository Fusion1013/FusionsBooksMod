package com.fusion.fusionsbookslib.block;

import com.fusion.fusionsbookslib.block.tile.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockAltar extends Block {

    public BlockAltar(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModTileEntityTypes.ALTAR.get().create();
    }
}
