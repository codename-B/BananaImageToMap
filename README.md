# A bukkit plugin that allows you to turn any image into a minecraft world!  

Includes:  

 *	CavePopulator  
 *	DesertPopulator  
 *	DungeonPopulator  
 *	FlowerPopulator  
 *	LakePopulator  
 *	Mushroom Populator  
 *	QuarryPopulator  
 *	Ruins Populator - thanks @Nightgunner5  
 *	Snow Populator  
 *	SpookyRoomPopulator  
 *	TorchPopulator - thanks @Nightgunner5  
 *	Tree Populator - thanks @heldplayer and @SpaceManiac  
 *	Ores! - thanks @Notch  

## Usage:

There are 2 ways to use this plugin, either through bukkit.yml or an external world manager, such as MultiVerse 2.0, that supports custom world generation.  

### Using bukkit.yml:
 
To set up a world using bukkit.yml, insert the following in your bukkit.yml configuration file:  

	worlds:
		{worldname}:
			generator: BananaImageToMap:{imagefile}

Make sure to replace {imagefile} with the name of the file *including the extension* that you  
put in your plugins/BananaImageToMap/ folder and {worldname} with the name of the world you wish to genenerate.  

### Using Multiverse 2

Using Multiverse 2, which is to the best of my knowledge the only world management plugin that supports custom generators, the command is  

	/mvcreate {worldname} NORMAL -g BananaImageToMap:{imagename}

Again, replacing {worldname} and {imagename} with the world and image name respectfully. After that you can use it like any normal MV world.
