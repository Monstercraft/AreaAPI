package org.monstercraft.area.api.wrappers;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.monstercraft.area.AreaAPI;
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;
import org.monstercraft.area.api.exception.InvalidWorldException;

public class HeightlessArea extends Area {

    private double maxx;

    private double minx;

    private double maxz;

    private double minz;

    private final World world;

    /**
     * Creates a heightless area using 2 blocks locations.
     *
     * @param b1
     *            The first block.
     * @param b2
     *            The second block.
     * @throws InvalidWorldException
     *             Thrown when the players are in different worlds.
     */
    public HeightlessArea(final Block b1, final Block b2)
            throws InvalidWorldException {
        this(b1.getLocation(), b2.getLocation(), "Blocks");
    }

    /**
     * Creates a heightless area using 2 locations.
     *
     * @param l1
     *            The first location.
     * @param l2
     *            The second location.
     * @throws InvalidWorldException
     *             Thrown when the players are in different worlds.
     */
    public HeightlessArea(final Location l1, final Location l2)
            throws InvalidWorldException {
        this(l1, l2, "Locations");
    }

    private HeightlessArea(final Location l1, final Location l2,
            final String type) throws InvalidWorldException {
        if (!l1.getWorld().equals(l2.getWorld())) {
            throw new InvalidWorldException(type + " on seprate worlds");
        }
        maxx = AreaAPI.getMaxX(l1, l2);
        maxz = AreaAPI.getMaxZ(l1, l2);
        minx = AreaAPI.getMinX(l1, l2);
        minz = AreaAPI.getMinZ(l1, l2);
        world = l1.getWorld();
    }

    /**
     * Creates a heightless area using 2 players locations.
     *
     * @param p1
     *            The first player.
     * @param p2
     *            The second player.
     * @throws InvalidWorldException
     *             Thrown when the players are in different worlds.
     */
    public HeightlessArea(final Player p1, final Player p2)
            throws InvalidWorldException {
        this(p1.getLocation(), p2.getLocation(), "Players");
    }

    /**
     * Checks if the block is within the area.
     *
     * @param block
     *            The block to check.
     * @return True if the area contains the block; otherwise false.
     */
    @Override
    public boolean contains(final Block block) {
        return this.contains(block.getLocation());
    }

    /**
     * Checks if the location is within the area.
     *
     * @param loc
     *            The player to check.
     * @return True if the area contains the location; otherwise false.
     */
    @Override
    public boolean contains(final Location loc) {
        return loc.getWorld().equals(world) ? loc.getX() <= maxx
                && loc.getZ() <= maxz && loc.getX() >= minx
                && loc.getZ() >= minz : false;
    }

    /**
     * Checks if the player is within the area.
     *
     * @param player
     *            The player to check.
     * @return True if the area contains the player; otherwise false.
     */
    @Override
    public boolean contains(final Player player) {
        return this.contains(player.getLocation());
    }

    /**
     * Expands the area.
     *
     * @param d
     *            The direction to expand.
     * @param amount
     *            The size to expand in the specified directions.
     * @throws InvalidDirectionException
     *             Thrown when the specified direction is invalid for the Area.
     */
    @Override
    public void expand(final Direction d, final int amount)
            throws InvalidDirectionException {
        if (d.equals(Direction.ALL) || d.equals(Direction.UP)
                || d.equals(Direction.DOWN)) {
            throw new InvalidDirectionException("Cannot expand in direction "
                    + d.toString());
        } else if (d.equals(Direction.SIDES)) {
            maxz += amount;
            maxx += amount;
            minx -= amount;
            minz -= amount;
        } else if (d.equals(Direction.NORTH)) {
            minz -= amount;
        } else if (d.equals(Direction.SOUTH)) {
            maxz += amount;
        } else if (d.equals(Direction.EAST)) {
            maxx += amount;
        } else if (d.equals(Direction.WEST)) {
            minx -= amount;
        }
    }

    /**
     * Fetches the blocks within the area.
     *
     * @return The blocks within the area.
     */
    @Override
    public Block[] getBlocks() {
        final ArrayList<Block> tileList = new ArrayList<Block>();
        int y = 0;
        do {
            int x = (int) this.getMinX();
            do {
                int z = (int) this.getMinZ();
                do {
                    if (this.contains(world.getBlockAt(x, y, z))) {
                        tileList.add(world.getBlockAt(x, y, z));
                    }
                    z++;
                } while (z <= this.getMaxZ());
                x++;
            } while (x <= this.getMaxX());
            y++;
        } while (y <= world.getMaxHeight());
        final Block[] tileArray = new Block[tileList.size()];
        return tileList.toArray(tileArray);
    }

    /**
     * The length.
     *
     * @return The length of the area.
     */
    public double getLength() {
        return maxx - minx;
    }

    /**
     * The maximum X value.
     *
     * @return The maximum X value.
     */
    public double getMaxX() {
        return maxx;
    }

    /**
     * The maximum Z value.
     *
     * @return The maximum Z value.
     */
    public double getMaxZ() {
        return maxz;
    }

    /**
     * The minimum X value.
     *
     * @return The minimum X value.
     */
    public double getMinX() {
        return minx;
    }

    /**
     * The minimum Z value.
     *
     * @return The minimum Z value.
     */
    public double getMinZ() {
        return minz;
    }

    /**
     * Fetches the total amount of blocks within the area.
     *
     * @return The total amount of blocks within the area.
     */
    @Override
    public int getTotalBlocks() {
        return (int) this.getLength() * (int) this.getWidth()
                * world.getMaxHeight();
    }

    /**
     * The width.
     *
     * @return The width of the area.
     */
    public double getWidth() {
        return maxz - minz;
    }

    /**
     * The world.
     *
     * @return The world the area was created in.
     */
    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void shift(final Direction direction, final int amount)
            throws InvalidDirectionException {
        // TODO Auto-generated method stub

    }

    /**
     * Shrinks the area.
     *
     * @param d
     *            The direction to shrink.
     * @param amount
     *            The size to shrink in the specified directions.
     * @throws InvalidDirectionException
     *             Thrown when the specified direction is invalid for the Area.
     */
    @Override
    public void shrink(final Direction d, final int amount)
            throws InvalidDirectionException {
        if (d.equals(Direction.ALL) || d.equals(Direction.UP)
                || d.equals(Direction.DOWN)) {
            throw new InvalidDirectionException("Cannot expand in direction "
                    + d.toString());
        } else if (d.equals(Direction.SIDES)) {
            maxz -= amount;
            maxx -= amount;
            minx += amount;
            minz += amount;
        } else if (d.equals(Direction.NORTH)) {
            minz += amount;
        } else if (d.equals(Direction.SOUTH)) {
            maxz -= amount;
        } else if (d.equals(Direction.EAST)) {
            maxx -= amount;
        } else if (d.equals(Direction.WEST)) {
            minx += amount;
        }
    }

}
