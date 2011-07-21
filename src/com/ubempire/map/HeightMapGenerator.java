package com.ubempire.map;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.World;


/**
 * ChunkGenerator that generates terrain based on a HeightMap.
 */
public class HeightMapGenerator extends ChunkGenerator {

/*    private static final Material[] ORE_LIST = { Material.DIAMOND_ORE, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GRAVEL };
    private static final int[] maxDistFromOrigin = { 2, 4, 3, 2, 2, 2, 5 };
    private static final int[] minBlocksPerNode = { 2, 4, 3, 2, 2, 2, 6 };
    private static final int[] maxBlocksPerNode = { 4, 8, 5, 4, 3, 3, 10 };
    private static final int[] minNodesPerChunk = { 1, 10, 7, 4, 5, 5, 5 };
    private static final int[] maxNodesPerChunk = { 5, 50, 30, 8, 20, 8, 10 };
    private static final int[][] chancePer16Levels = {
        { 30, 65, 95, 100, 0, 0, 0, 0 },
        { 12, 25, 37, 90, 62, 75, 87, 100 },
        { 20, 45, 70, 80, 90, 95, 100, 0 },
        { 20, 60, 80, 100, 0, 0, 0, 0 },
        { 20, 50, 80, 100, 0, 0, 0, 0 },
        { 20, 50, 80, 100, 0, 0, 0, 0 },
        { 20, 30, 50, 100, 50, 60, 70, 100 } };
  /*/  protected byte[] result;
    private HeightMap heightMap;

    public HeightMapGenerator(HeightMap heightMap) {
        this.heightMap = heightMap;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.<BlockPopulator>asList(new MetaPopulator());
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public byte[] generate(World world, Random rand, int chunkx, int chunkz) {
        result = new byte[16 * 16 * 128];
        int xbound = heightMap.getXBound();
        int zbound = heightMap.getZBound();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 55; y >= 0; y--) {
                    if (y <= 50) {
                        set(x, y, z, Material.STONE);
                    } else if (y < 2) {
                        set(x, y, z, Material.BEDROCK);
                    } else {
                        set(x, y, z, Material.STATIONARY_WATER);
                    }
                }
            }
        }

        @SuppressWarnings("unused")
		int cx2 = chunkx, cz2 = chunkz;

        while (chunkx < -xbound) {
            chunkx += 2 * xbound;
        }
        while (chunkz < -zbound) {
            chunkz += 2 * zbound;
        }
        while (chunkx > xbound) {
            chunkx -= 2 * xbound;
        }
        while (chunkz > zbound) {
            chunkz -= 2 * zbound;
        }

        int startx = (chunkx + xbound) * 16;
        int startz = (chunkz + zbound) * 16;

        //System.out.println(cx2 + "," + cz2 + " " + xbound + "," + zbound + " " + chunkx + "," + chunkz + " " + startx + "," + startz);

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int y = 50 + heightMap.get(startx + x, startz + z);
                if (y < 55) {
                    set(x, y, z, Material.DIRT);
                } else {
                    set(x, y, z, Material.GRASS);
                }
                int count = 0;
                for (y = y - 1; y >= 0; y--) {
                    if (count < 2) {
                        set(x, y, z, Material.DIRT);
                        count++;
                    } else {
                        set(x, y, z, Material.STONE);
                    }
                }
                set(x, 1, z, Material.BEDROCK);
            }
        }

  /*      int layer;
        int nodeAmount;

        for (int currOre = 0; currOre < ORE_LIST.length; currOre++) {
            nodeAmount = rand.nextInt(maxNodesPerChunk[currOre] - minNodesPerChunk[currOre] + 1) + minNodesPerChunk[currOre];

            for (int currNode = 0; currNode < nodeAmount; currNode++) {
                layer = rand.nextInt(100) + 1;

                for (int currentLayer = 0; currentLayer < chancePer16Levels[currOre].length; currentLayer++) {
                    if (layer <= chancePer16Levels[currOre][currentLayer]) {
                        genOreAtLayer(16 * currentLayer, currOre, rand);
                        break;
                    }
                }
            }/*/
        //}

        return result;
    }

   /* private void genOreAtLayer(int layer, int currOre, Random random) {
        Material currMaterial = ORE_LIST[currOre];

        int y = random.nextInt(16) + layer;
        int x = random.nextInt(15);
        int z = random.nextInt(15);

        if (result[(x * 16 + z) * 128 + y] == (byte) Material.STONE.getId()) {
            List<int[]> candidates = new ArrayList<int[]>();
            int[] coreBlock = new int[3];
            coreBlock[0] = x;
            coreBlock[1] = y;
            coreBlock[2] = z;
            result[(x * 16 + z) * 128 + y] = (byte) currMaterial.getId();

            for (int xMod = -maxDistFromOrigin[currOre]; xMod <= maxDistFromOrigin[currOre]; xMod++) {
                for (int yMod = -maxDistFromOrigin[currOre]; yMod <= maxDistFromOrigin[currOre]; yMod++) {
                    for (int zMod = -maxDistFromOrigin[currOre]; zMod <= maxDistFromOrigin[currOre]; zMod++) {
                        if ((x + xMod) < 0 || (x + xMod) > 15 || (y + yMod) < 0 || (y + yMod) > 127 || (z + zMod) < 0 || (z + zMod) > 15) {
                            continue;
                        } else if (get(x + xMod, y + yMod, z + zMod) == (byte) Material.STONE.getId()) {
                            coreBlock[0] = (x + xMod);
                            coreBlock[1] = (y + yMod);
                            coreBlock[2] = (z + zMod);
                            candidates.add(coreBlock);
                        }
                    }
                }
            }

            if (candidates.size() > 0) {
                Collections.shuffle(candidates);

                int blocksAtNode = random.nextInt(maxBlocksPerNode[currOre] - minBlocksPerNode[currOre] + 1) + minBlocksPerNode[currOre];
                for (int[] node : candidates) {
                    set(node[0], node[1], node[2], currMaterial);
                    if (--blocksAtNode <= 0) {
                        break;
                    }
                }
            }
        }
    }
/*/
    private void set(int x, int y, int z, Material mat) {
        result[(x * 16 + z) * 128 + y] = (byte) mat.getId();
    }

    @SuppressWarnings("unused")
	private byte get(int x, int y, int z) {
        return result[(x * 16 + z) * 128 + y];
    }

}