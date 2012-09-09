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

	private World world;

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
	public CubedArea(Block b1, Block b2) throws InvalidWorldException {
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
	public CubedArea(Location l1, Location l2) throws InvalidWorldException {
		this(l1, l2, "Locations");
	}

	private CubedArea(Location l1, Location l2, String type)
			throws InvalidWorldException {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new InvalidWorldException(type + " on seprate worlds");
		}
		this.maxx = AreaAPI.getMaxX(l1, l2);
		this.maxy = AreaAPI.getMaxY(l1, l2);
		this.minx = AreaAPI.getMinX(l1, l2);
		this.miny = AreaAPI.getMinY(l1, l2);
		this.maxz = AreaAPI.getMaxZ(l1, l2);
		this.minz = AreaAPI.getMinZ(l1, l2);
		this.world = l1.getWorld();
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
	public CubedArea(Player p1, Player p2) throws InvalidWorldException {
		this(p1.getLocation(), p2.getLocation(), "Players");
	}

	/**
	 * Checks if the block is within the area.
	 * 
	 * @param block
	 *            The block to check.
	 * @return True if the area contains the block; otherwise false.
	 */
	public boolean contains(Block block) {
		return contains(block.getLocation());
	}

	/**
	 * Checks if the location is within the area.
	 * 
	 * @param loc
	 *            The player to check.
	 * @return True if the area contains the location; otherwise false.
	 */
	public boolean contains(Location loc) {
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
	public boolean contains(Player player) {
		return contains(player.getLocation());
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
	public void expand(Direction d, int amount)
			throws InvalidDirectionException {
		if (d.equals(Direction.ALL)) {
			if (this.miny - amount < 0) {
				throw new InvalidDirectionException("Minimum height reached");
			}
			if (this.maxy + amount > world.getMaxHeight()) {
				throw new InvalidDirectionException("Maximum height reached");
			}
			this.maxz += amount;
			this.maxx += amount;
			this.maxy += amount;
			this.minx -= amount;
			this.minz -= amount;
			this.miny -= amount;
		} else if (d.equals(Direction.SIDES)) {
			this.maxz += amount;
			this.maxx += amount;
			this.minx -= amount;
			this.minz -= amount;
		} else if (d.equals(Direction.UP)) {
			if (this.maxy + amount > world.getMaxHeight()) {
				throw new InvalidDirectionException("Maximum height reached");
			}
			this.maxy += amount;
		} else if (d.equals(Direction.DOWN)) {
			if (this.miny - amount < 0) {
				throw new InvalidDirectionException("Minimum height reached");
			}
			this.miny -= amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx -= amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz -= amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz += amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx += amount;
		} else {
			throw new InvalidDirectionException("Invalid direction specified");
		}
	}

	/**
	 * Fetches the blocks within the area.
	 * 
	 * @return The blocks within the area.
	 */
	public Block[] getBlocks() {
		ArrayList<Block> tileList = new ArrayList<Block>();
		int y = (int) getMinY();
		do {
			int x = (int) getMinX();
			do {
				int z = (int) getMinZ();
				do {
					if (this.contains(world.getBlockAt(x, y, z))) {
						tileList.add(world.getBlockAt(x, y, z));
					}
					z++;
				} while (z <= getMaxZ());
				x++;
			} while (x <= getMaxX());
			y++;
		} while (y <= getMaxY());
		Block[] tileArray = new Block[tileList.size()];
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
	public World getWorld() {
		return world;
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
	public void shrink(Direction d, int amount)
			throws InvalidDirectionException {
		if (d.equals(Direction.ALL)) {
			this.maxz -= amount;
			this.maxx -= amount;
			this.maxy -= amount;
			this.minx += amount;
			this.minz += amount;
			this.miny += amount;
		} else if (d.equals(Direction.SIDES)) {
			this.maxz -= amount;
			this.maxx -= amount;
			this.minx += amount;
			this.minz += amount;
		} else if (d.equals(Direction.UP)) {
			this.maxy -= amount;
		} else if (d.equals(Direction.DOWN)) {
			this.miny += amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz += amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz -= amount;
		} else if (d.equals(Direction.EAST)) {
			if (this.maxx - amount < minx) {
				throw new InvalidDirectionException("");
			}
			this.maxx -= amount;
		} else if (d.equals(Direction.WEST)) {
			if (this.minx + amount > maxx) {
				throw new InvalidDirectionException("");
			}
			this.minx += amount;
		} else {
			throw new InvalidDirectionException("Invalid direction specified");
		}
	}

	/**
	 * Fetches the total amount of blocks within the area.
	 * 
	 * @return The total amount of blocks within the area.
	 */
	public int getTotalBlocks() {
		return (int) getLength() * (int) getWidth() * (int) getHeight();
	}

	public void shift(Direction direction, int amount) {
		// TODO Auto-generated method stub

	}
}
