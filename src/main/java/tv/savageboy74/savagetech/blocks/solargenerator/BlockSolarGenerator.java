package tv.savageboy74.savagetech.blocks.solargenerator;

/*
 * BlockSolarGenerator.java
 * Copyright (C) 2016 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

//public class BlockSolarGenerator extends STBlock implements ITileEntityProvider
//{
//    protected final int maxEnergyGeneration;
//    protected final int maxEnergyTransfer;
//    protected final int maxEnergyCapacity;
//
//    public BlockSolarGenerator(int energyGeneration, int energyCapacity) {
//        super(Material.rock);
//        this.maxEnergyGeneration = energyGeneration;
//        this.maxEnergyCapacity = energyCapacity;
//        this.maxEnergyTransfer = maxEnergyGeneration * 4;
//        this.isBlockContainer = true;
//        this.setSoundType(SoundType.STONE);
//        this.setHardness(3.0F);
//        this.setResistance(5.0F);
//        this.setUnlocalizedName(Names.Blocks.generator);
//    }
//
//    public int getMaxEnergyGeneration() {
//        return maxEnergyGeneration;
//    }
//
//    public int getMaxEnergyTransfer() {
//        return maxEnergyTransfer;
//    }
//
//    public int getMaxEnergyCapacity() {
//        return maxEnergyCapacity;
//    }
//
//    @Override
//    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
//        if (stack.hasTagCompound()) {
//            TileEntitySolarGenerator tileGenerator = (TileEntitySolarGenerator) worldIn.getTileEntity(pos);
//            tileGenerator.setEnergyStored(stack.getTagCompound().getInteger(Names.NBT.ENERGY));
//        }
//
//        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
//    }
//
//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
//        if(WorldHelper.isServer(worldIn)) {
//            if(!playerIn.isSneaking() && worldIn.getTileEntity(pos) instanceof TileEntitySolarGenerator) {
//                playerIn.openGui(SavageTech.instance, Names.Gui.SOLAR_GENERATOR_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
//        return super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World worldIn, int meta) {
//        return new TileEntitySolarGenerator(maxEnergyGeneration, maxEnergyTransfer, maxEnergyCapacity);
//    }
//}
