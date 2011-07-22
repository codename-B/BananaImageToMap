package com.ubempire.map;

import java.io.File;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for BananaImageToMap
 */
public class ImageToMapPlugin extends JavaPlugin {

    private ChunkGenerator generator;
    private String imageFile;

    @Override
    public void onEnable() {
        PluginDescriptionFile description = getDescription();
        File pluginDir = new File("plugins/BananaImageToMap/");
        if (!pluginDir.exists()){pluginDir.mkdir();System.out.println(description.getFullName() + " Directory doesn't exist! Creating it.");};
        
      /*  // Removed, instead wait until called by bukkit.yml or an external manager
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (!defaultWorld) {
                    // We only read the worldName if we need it in order to keep the config cleaner
                    String worldName = getConfiguration().getString("world", "noisy");
                    getConfiguration().save();
                    
                    if (getGenerator() == null) {
                        return;
                    }
                    getServer().createWorld(worldName, Environment.NORMAL, generator);
                }
            }
        });*/

        // Note here: huge success
        System.out.println(description.getFullName() + " is now enabled");
    }

    @Override
    public void onDisable() {
        System.out.println(getDescription().getFullName() + " is now disabled");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return getGenerator(id);
    }

    private ChunkGenerator getGenerator(String id) {
        if (generator == null) {
	        // Generate a new map if need be
		if (id == null) {id = "earth";};
		imageFile = "plugins/BananaImageToMap/" + id + ".jpg";
		if (!new File(imageFile).exists()) {
			long start = System.currentTimeMillis();
          	System.out.println("[" + getDescription().getName() + "] Writing heightmap to " + imageFile + "...");
          	DrawJPG.write(1024, 1024, imageFile);
          	double time = (double)((System.currentTimeMillis() - start) / 100) / 10;
          	System.out.println("[" + getDescription().getName() + "] Completed in " + time + " seconds");
      		}
        long start = System.currentTimeMillis();
		System.out.println("[" + getDescription().getName() + "] Reading heightmap from " + imageFile + "...");
		HeightMap heightMap = new HeightMap(imageFile);
		double time = (double)((System.currentTimeMillis() - start) / 100) / 10;
		System.out.println("[" + getDescription().getName() + "] Read " + heightMap.getWidth() + "x" + heightMap.getHeight() + " pixels in " + time + " seconds");
		generator = new HeightMapGenerator(heightMap);
        }

        return generator;
    }

}
