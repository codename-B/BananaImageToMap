package com.ubempire.map;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;


/**
 * BlockPopulator that calls all other populators in a specific sequence.
 */
public class MetaPopulator extends BlockPopulator {
    
    private final BlockPopulator[] list;
    
    public MetaPopulator() {
        list = new BlockPopulator[] {
            // In-ground
            new QuarryPopulator(), new LakePopulator(),
            // On-ground
            // Desert is before tree and mushroom but snow is after so trees have snow on top
            new DesertPopulator(), new RuinsPopulator(), new TreePopulator(), new MushroomPopulator(), new SnowPopulator(), new FlowerPopulator(), 
            // Below-ground
            new SpookyRoomPopulator(), new DungeonPopulator(), new CavePopulator(), new OrePopulator(), new TorchPopulator()
        };
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        for (BlockPopulator pop : list) {
            pop.populate(world, random, chunk);
        }
    }
    
}
