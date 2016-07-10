package net.imaginecraft.plugin;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmptyWorldGenerator extends ChunkGenerator {
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<>();
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return false;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz, ChunkGenerator.BiomeGrid biomes) {
        byte[][] blocks = new byte[world.getMaxHeight() / 16][];
        Vector spawn = new Vector(8, world.getMaxHeight() / 2, 8); // 8 so it is in center of chunk
        if (chunkx == 0 && chunkz == 0)
            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++)
                    setBlock(blocks, x, spawn.getBlockY(), z, (byte) Material.STONE.getId());
        world.setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        return blocks;
    }

    private void setBlock(byte[][] result, int x, int y, int z, byte blockId) {
        // is this chunk part already initialized?
        if (result[y >> 4] == null) {
            // Initialize the chunk part
            result[y >> 4] = new byte[4096];
        }
        // set the block (look above, how this is done)
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blockId;
    }
}
