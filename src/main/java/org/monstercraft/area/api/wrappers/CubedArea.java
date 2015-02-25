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

public class CubedArea extends Area {

    private double maxx;

    private double minx;

    private double maxy;

    private double miny;

    private double maxz;

    private double minz;

    private final World world;

    /**
     * Creates a CubedArea using 2 blocks locations at the time created.
     *
     * @param b1
     *            The first block.
     * @param b2
     *            The second block.
     * @throws InvalidWorldException
     *             Thrown when blocks are located on seprate worlds.
     */
    public CubedArea(final Block b1, final Block b2)
            throws InvalidWorldException {
        this(b1.getLocation(), b2.getLocation(), "Blocks");
    }

    /**
     * Creates a CubedArea using 2 locations at the time created.
     *
     * @param l1
     *            The first location.
     * @param l2
     *            The second location.
     * @throws InvalidWorldException
     *             Thrown when blocks are located on seprate worlds.
     */
    public CubedArea(final Location l1, final Location l2)
            throws InvalidWorldException {
        this(l1, l2, "Locations");
    }

    private CubedArea(final Location l1, final Location l2, final String type)
            throws InvalidWorldException {
        if (!l1.getWorld().equals(l2.getWorld())) {
            throw new InvalidWorldException(type + " on seprate worlds");
        }
        maxx = AreaAPI.getMaxX(l1, l2);
        maxy = AreaAPI.getMaxY(l1, l2);
        minx = AreaAPI.getMinX(l1, l2);
        miny = AreaAPI.getMinY(l1, l2);
        maxz = AreaAPI.getMaxZ(l1, l2);
        minz = AreaAPI.getMinZ(l1, l2);
        world = l1.getWorld();
    }

    /**
     * Creates a CubedArea using 2 players locations at the time created.
     *
     * @param p1
     *            The first player.
     * @param p2
     *            The second player.
     * @throws InvalidWorldException
     *             Thrown when the players are located on seprate worlds.
     */
    public CubedArea(final Player p1, final Player p2)
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
                && loc.getY() <= maxy && loc.getX() >= minx
                && loc.getY() >= miny && loc.getZ() <= maxz
                && loc.getZ() >= minz && loc.getZ() <= maxz : false;
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
        if (d.equals(Direction.ALL)) {
            if (miny - amount < 0) {
                throw new InvalidDirectionException("Minimum height reached");
            }
            if (maxy + amount > world.getMaxHeight()) {
                throw new InvalidDirectionException("Maximum height reached");
            }
            maxz += amount;
            maxx += amount;
            maxy += amount;
            minx -= amount;
            minz -= amount;
            miny -= amount;
        } else if (d.equals(Direction.SIDES)) {
            maxz += amount;
            maxx += amount;
            minx -= amount;
            minz -= amount;
        } else if (d.equals(Direction.UP)) {
            if (maxy + amount > world.getMaxHeight()) {
                throw new InvalidDirectionException("Maximum height reached");
            }
            maxy += amount;
        } else if (d.equals(Direction.DOWN)) {
            if (miny - amount < 0) {
                throw new InvalidDirectionException("Minimum height reached");
            }
            miny -= amount;
        } else if (d.equals(Direction.WEST)) {
            minx -= amount;
        } else if (d.equals(Direction.NORTH)) {
            minz -= amount;
        } else if (d.equals(Direction.SOUTH)) {
            maxz += amount;
        } else if (d.equals(Direction.EAST)) {
            maxx += amount;
        } else {
            throw new InvalidDirectionException("Invalid direction specified");
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
        int y = (int) this.getMinY();
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
        } while (y <= this.getMaxY());
        final Block[] tileArray = new Block[tileList.size()];
        return tileList.toArray(tileArray);
    }

    /**
     * The height of the area.
     *
     * @return The height of the area.
     */
    public double getHeight() {
        return maxy - miny;
    }

    /**
     * The length of the area.
     *
     * @return The length of the area.
     */
    public double getLength() {
        return maxx - minx;
    }

    /**
     * The maximum X value of the area.
     *
     * @return The maximum X value of the area.
     */
    public double getMaxX() {
        return maxx;
    }

    /**
     * The maximum Y value of the area.
     *
     * @return The maximum Y value of the area.
     */
    public double getMaxY() {
        return maxy;
    }

    /**
     * The maximum Z value of the area.
     *
     * @return The maximum Z value of the area.
     */
    public double getMaxZ() {
        return maxz;
    }

    /**
     * The minimum X value of the area.
     *
     * @return The minimum X value of the area.
     */
    public double getMinX() {
        return minx;
    }

    /**
     * The minimum Y value of the area.
     *
     * @return The minimum Y value of the area.
     */
    public double getMinY() {
        return miny;
    }

    /**
     * The minimum Z value of the area.
     *
     * @return The minimum Z value of the area.
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
                * (int) this.getHeight();
    }

    /**
     * The width of the area.
     *
     * @return The width of the area.
     */
    public double getWidth() {
        return maxz - minz;
    }

    /**
     * The world the area was created in.
     *
     * @return The world the area is located in.
     */
    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void shift(final Direction direction, final int amount) {
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
        if (d.equals(Direction.ALL)) {
            maxz -= amount;
            maxx -= amount;
            maxy -= amount;
            minx += amount;
            minz += amount;
            miny += amount;
        } else if (d.equals(Direction.SIDES)) {
            maxz -= amount;
            maxx -= amount;
            minx += amount;
            minz += amount;
        } else if (d.equals(Direction.UP)) {
            maxy -= amount;
        } else if (d.equals(Direction.DOWN)) {
            miny += amount;
        } else if (d.equals(Direction.NORTH)) {
            minz += amount;
        } else if (d.equals(Direction.SOUTH)) {
            maxz -= amount;
        } else if (d.equals(Direction.EAST)) {
            if (maxx - amount < minx) {
                throw new InvalidDirectionException("");
            }
            maxx -= amount;
        } else if (d.equals(Direction.WEST)) {
            if (minx + amount > maxx) {
                throw new InvalidDirectionException("");
            }
            minx += amount;
        } else {
            throw new InvalidDirectionException("Invalid direction specified");
        }
    }
}
