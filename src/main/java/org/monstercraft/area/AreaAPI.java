package org.monstercraft.area;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.monstercraft.area.metrics.Metrics;

public class AreaAPI extends JavaPlugin implements Listener {

    /**
     * Checks the maximum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The maximum X value.
     */
    public static double getMaxX(final Location l1, final Location l2) {
        return Math.max(l1.getX(), l2.getX());
    }

    /**
     * Checks the maximum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The maximum Y value.
     */
    public static double getMaxY(final Location l1, final Location l2) {
        return Math.max(l1.getY(), l2.getY());
    }

    /**
     * Checks the maximum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The maximum Z value.
     */
    public static double getMaxZ(final Location l1, final Location l2) {
        return Math.max(l1.getZ(), l2.getZ());
    }

    /**
     * Checks the minimum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The minimum X value.
     */
    public static double getMinX(final Location l1, final Location l2) {
        return Math.min(l1.getX(), l2.getX());
    }

    /**
     * Checks the minimum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The minimum Y value.
     */
    public static double getMinY(final Location l1, final Location l2) {
        return Math.min(l1.getY(), l2.getY());
    }

    /**
     * Checks the minimum value between the 2 locations specified.
     *
     * @param l1
     *            The 1st location.
     * @param l2
     *            The 2nd location.
     * @return The minimum Z value
     */
    public static double getMinZ(final Location l1, final Location l2) {
        return Math.min(l1.getZ(), l2.getZ());
    }

    @Override
    public void onEnable() {
        try {
            new Metrics(this).start();
        } catch (final IOException e) {
            Bukkit.getLogger().info("Error setting up metrics for AreaAPI");
        }
    }

}
