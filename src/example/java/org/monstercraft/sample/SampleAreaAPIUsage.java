package org.monstercraft.sample;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
// Start of AreaAPI imports
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;
import org.monstercraft.area.api.exception.InvalidPlaneException;
import org.monstercraft.area.api.exception.InvalidWorldException;
import org.monstercraft.area.api.wrappers.CubedArea;
import org.monstercraft.area.api.wrappers.HeightlessArea;
import org.monstercraft.area.api.wrappers.PolygonalArea;
import org.monstercraft.area.api.wrappers.SinglePlaneArea;

// end of AreaAPI imports

public class SampleAreaAPIUsage extends JavaPlugin implements Listener {

    // Create our areas and set them to null
    private CubedArea area1 = null;
    private HeightlessArea area2 = null;
    private SinglePlaneArea area3 = null;
    private PolygonalArea area4 = null;

    @Override
    public void onEnable() {
        try {
            /*
             * Here I have create a cubed area using 2 blocks within my world "test". This will create the area based off of the blocks minimum x,y
             * and z values and their maximum x,y and z values.
             *
             * An InvalidWorldException will be thrown if the blocks specified are on different worlds.
             */
            area1 = new CubedArea(Bukkit.getServer().getWorld("test")
                    .getBlockAt(10, 10, 10), Bukkit.getServer()
                    .getWorld("test").getBlockAt(20, 20, 20));

            /*
             * The second type of area I have created is a heightless area. This area will use the 2 blocks specified for their x and z values. The
             * area created will have no height limit up or down, essintally being heightless.
             *
             * An InvalidWorldException will be thrown if the blocks specified are on different worlds.
             */
            area2 = new HeightlessArea(Bukkit.getServer().getWorld("test")
                    .getBlockAt(10, 10, 10), Bukkit.getServer()
                    .getWorld("test").getBlockAt(0, 0, 0));

            /*
             * This third type of area is a single plane area. This type of area will use 2 blocks that have the same Y value to create an area. The
             * height of the area will be 1 however the width and length will use the minimum and maximum x and z values of the 2 blocks specfied.
             *
             * An InvalidPlaneExcpetion will be thrown when the blocks specified have different Y values.
             *
             * An InvalidWorldException will be thrown if the blocks specified are on different worlds.
             */
            area3 = new SinglePlaneArea(Bukkit.getServer().getWorld("test")
                    .getBlockAt(-10, 65, -10), Bukkit.getServer()
                    .getWorld("test").getBlockAt(10, 65, 10));

            /*
             * This last type of area will be created using an array of blocks that you specify. All the blocks specfied will be added to the area.
             */
            area4 = new PolygonalArea(
                    new Block[] {
                            Bukkit.getServer().getWorld("test")
                                    .getBlockAt(-9, 64, 6),
                            Bukkit.getServer().getWorld("test")
                                    .getBlockAt(-10, 64, 6),
                            Bukkit.getServer().getWorld("test")
                                    .getBlockAt(-15, 64, 6),
                            Bukkit.getServer().getWorld("test")
                                    .getBlockAt(-12, 64, 4) });
        } catch (final InvalidWorldException e1) {
            e1.printStackTrace();
        } catch (final InvalidPlaneException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlacement(final BlockPlaceEvent event) {
        final Block b = event.getBlock();
        /*
         * Here we check if each individual area contains the block that was placed
         */
        if (area1.contains(b)) {
            /*
             * I then can expand the area. In this example I want to expand it in all directions by 5 blocks. This will update the maximum and minimum
             * values of the area that we have expanded.
             *
             * An InvalidDirectionException will be thrown when the direction specified is no valid for example you want to expand less than 0.
             */
            try {
                area1.expand(Direction.ALL, 5);
            } catch (final InvalidDirectionException e) {
                e.printStackTrace();
            }
        }
        if (area2.contains(b)) {
            /*
             * I can also shrink the area. In this example I want to shrink it on its east side by 5 blocks. This will update the maximum and minimum
             * values of the area that we have shrank.
             *
             * An InvalidDirectionException will be thrown when the direction specified is no valid for example you want to shrink by 5 east when the
             * width of the area is 3.
             */
            try {
                area2.shrink(Direction.EAST, 5);
            } catch (final InvalidDirectionException e) {
                e.printStackTrace();
            }
        }
        if (area3.contains(b)) {
            /*
             * In this example I want to get all of the blocks of the area so I can change them to stone. area#getBlocks will return an array of
             * blocks which I can then utilize.
             */
            final Block[] blocks = area3.getBlocks();
            for (final Block block : blocks) {
                block.setType(Material.STONE);
            }
        }
        if (area4.contains(b)) {
            /*
             * If the last area contains the block specified I just want to tell the console.
             */
            System.out.println("Yup the block was placed within the area4.");
        }
    }

}