package org.monstercraft.area;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.monstercraft.area.metrics.Metrics;

public class AreaAPI extends JavaPlugin implements Listener {

	public void onEnable() {
		try {
			new Metrics(this).start();
		} catch (IOException e) {
			Bukkit.getLogger().info("Error setting up metrics for AreaAPI");
		}
	}

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
