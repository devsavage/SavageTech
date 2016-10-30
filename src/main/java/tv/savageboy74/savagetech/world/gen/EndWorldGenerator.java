package tv.savageboy74.savagetech.world.gen;

/*
 * EndWorldGenerator.java
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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.init.ModBlocks;

import java.util.Arrays;
import java.util.Random;

public class EndWorldGenerator implements IWorldGenerator
{
    private final WorldGenMinable rawSoulMatterGenerator = new WorldGenMinable(ModBlocks.blockOreRawSoulMatter.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.END_STONE));

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() != 1)
            return;

        generateEnd(world, random, chunkX, chunkZ);
    }

    private void generateEnd(World worldIn, Random rand, int chunkX, int chunkZ) {
        if(!Arrays.asList(ConfigBlacklist.BLOCK_BLACKLIST.getCurrentValues()).contains(ModBlocks.blockOreRawSoulMatter.getUnlocalizedName())) {
            for(int i = 0; i < 15; i++) {
                if(TerrainGen.generateOre(worldIn, rand, rawSoulMatterGenerator, new BlockPos(chunkX, 0, chunkZ), OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                    rawSoulMatterGenerator.generate(worldIn, rand, new BlockPos(chunkX * 16 + rand.nextInt(16), 20 + rand.nextInt(100), chunkZ * 16 + rand.nextInt(16)));
                }
            }
        }
    }
}
