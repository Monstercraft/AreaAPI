package org.monstercraft.area;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.monstercraft.area.api.exception.InvalidWorldException;
import org.monstercraft.area.api.wrappers.CubedArea;
import org.monstercraft.area.metrics.Metrics;

public class AreaAPI extends JavaPlugin implements Listener {

	// TESTING PHASE
	private CubedArea area;

	// END TESTING

	public void onEnable() {
		// START TESTING
		try {
			area = new CubedArea(Bukkit.getServer().getWorld("test")
					.getBlockAt(10, 10, 10), Bukkit.getServer()
					.getWorld("test").getBlockAt(-10, -10, -10));
		} catch (InvalidWorldException e1) {
			e1.printStackTrace();
		}
		Bukkit.getPluginManager().registerEvents(this, this);
		// END TESTING

		try {
			new Metrics(this).start();
		} catch (IOException e) {
			Bukkit.getLogger().info("Error setting up metrics for AreaAPI");
		}
	}

	// START TESTING
	@EventHandler
	public void onPlacement(BlockPlaceEvent event) {
		if (area.contains(event.getBlock())) {
			System.out.println("Yup the block was placed within the area.");
		}
	}

	// END TESTING

	public static double getMaxX(Location l1, Location l2) {
		return Math.max(l1.getX(), l2.getX());
	}

	public static double getMaxY(Location l1, Location l2) {
		return Math.max(l1.getY(), l2.getY());
	}

	public static double getMinX(Location l1, Location l2) {
		return Math.min(l1.getX(), l2.getX());
	}

	public static double getMinY(Location l1, Location l2) {
		return Math.min(l1.getY(), l2.getY());
	}

	public static double getMinZ(Location l1, Location l2) {
		return Math.min(l1.getZ(), l2.getZ());
	}

	public static double getMaxZ(Location l1, Location l2) {
		return Math.max(l1.getZ(), l2.getZ());
	}

}
