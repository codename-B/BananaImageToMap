package com.ubempire.map;

import java.io.File;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for BananaImageToMap
 */
public class ImageToMapPlugin extends JavaPlugin {

    @SuppressWarnings("unused")
	private boolean defaultWorld;
    private ChunkGenerator generator;
    private String imageFile;

    @Override
    public void onEnable() {
        PluginDescriptionFile description = getDescription();

        // Read our configuration
        imageFile = getConfiguration().getString("image", "plugins/BananaImageToMap/earth.jpg");
        getConfiguration().save();

        // Generate a new map if need be
        if (!new File(imageFile).exists()) {
            long start = System.currentTimeMillis();
            System.out.println("[" + description.getName() + "] Writing heightmap to " + imageFile + "...");
            DrawJPG.write(1024, 1024, imageFile);
            double time = (double)((System.currentTimeMillis() - start) / 100) / 10;
            System.out.println("[" + description.getName() + "] Completed in " + time + " seconds");
        }

  /*      
     	// Schedule an event to create the world later.
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
        });
*/
        // Note here: huge success
        System.out.println(description.getFullName() + " is now enabled");
    }

    @Override
    public void onDisable() {
        System.out.println(getDescription().getFullName() + " is now disabled");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        // If we get here before our scheduled event, then the plugin has been set in bukkit.yml
        defaultWorld = true;
        return getGenerator();
    }

    private ChunkGenerator getGenerator() {
        if (generator == null) {
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