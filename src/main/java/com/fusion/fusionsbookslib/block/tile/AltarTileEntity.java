package com.fusion.fusionsbookslib.block.tile;

import com.fusion.fusionsbookslib.util.helpers.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class AltarTileEntity extends TileEntity implements ITickableTileEntity {

    public int x, y, z, tick;
    public int dx, dz;
    boolean initialized = false;

    public ArrayList<ItemStack> items = new ArrayList<>();

    public AltarTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public AltarTileEntity() {
        super(ModTileEntityTypes.ALTAR.get());
    }

    @Override
    public void tick() {
        if (!initialized) init();
        tick++;
        if (tick == 1){
            tick = 0;
            if (y > 2) execute3();
        }
    }

    private void init(){
        initialized = true;
        x = this.pos.getX() - 1;
        y = this.pos.getY() - 1;
        z = this.pos.getZ() - 1;

        dx = 0;
        dz = 0;
        tick = 0;
    }

    private void execute3(){
        while (y > 2){
            BlockPos posToBreak = new BlockPos(this.x + dx, this.y, this.z + dz);
            safeDestroyBlock(posToBreak, true, null);
            y--;
        }
        if (dx < 100) dx++;
        else if (dz < 100) {
            dz++;
            dx = 0;
        }

        y = this.pos.getY() - 1;
    }

    private void execute2(){
        BlockPos posToBreak = new BlockPos(this.x + dx, this.y, this.z + dz);
        safeDestroyBlock(posToBreak, true, null);

        if (dx <= 1) dx++;
        else if (dz <= 1){
            dz++;
            dx = 0;
        }
        else {
            dx = 0;
            dz = 0;
            y--;
        }
    }

    private void execute(){
        int index = 0;
        Block[] blocksRemoved = new Block[9];
        for(int x = 0; x < 3; x++){
            for(int z = 0; z < 3; z++){
                BlockPos posToBreak = new BlockPos(this.x + x, this.y, this.z + z);
                blocksRemoved[index] = this.world.getBlockState(posToBreak).getBlock();
                safeDestroyBlock(posToBreak, true, null);
                index++;
            }
        }
        this.y -=1;
    }

    // Destroys the block at the position if it is not air
    // Drops the block if dropBlock is true
    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir(world, pos)) return false;
        else{
            IFluidState iFluidState = world.getFluidState(pos);
            world.playEvent(2011, pos, Block.getStateId(blockState));
            if (dropBlock){
                TileEntity tileEntity = blockState.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockState, world, this.pos.add(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);
            }
            return world.setBlockState(pos, iFluidState.getBlockState(), 3);
        }
    }

    // Destroys the block at the position if it is not air
    // Drops the block if dropBlock is true
    private boolean safeDestroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir(world, pos)) return false;
        else{
            IFluidState iFluidState = world.getFluidState(pos);
            world.playEvent(2011, pos, Block.getStateId(blockState));
            if (dropBlock && !isBlacklisted(blockState.getBlock())){
                TileEntity tileEntity = blockState.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockState, world, this.pos.add(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);
                return world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 3);
            }
            return true;
        }
    }

    private boolean isBlacklisted(Block block){
        for (Block b : blacklisted){
            if (b == block) return true;
        }
        return false;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues", NBTHelper.toNBT(this));
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        CompoundNBT initValues = compound.getCompound("initvalues");
        if (initValues != null){
            this.x = initValues.getInt("x");
            this.y = initValues.getInt("y");
            this.z = initValues.getInt("z");
            this.tick = 0;
            initialized = true;
            return;
        }
        init();
    }

    Block[] blacklisted = new Block[]{
            Blocks.COBBLESTONE,
            Blocks.STONE,
            Blocks.ANDESITE,
            Blocks.DIORITE,
            Blocks.GRANITE,
            Blocks.DIRT,
            Blocks.GRAVEL,
            Blocks.GRASS_BLOCK
    };
}
