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

	private World world;

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
	public HeightlessArea(Block b1, Block b2) throws InvalidWorldException {
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
	public HeightlessArea(Location l1, Location l2)
			throws InvalidWorldException {
		this(l1, l2, "Locations");
	}

	private HeightlessArea(Location l1, Location l2, String type)
			throws InvalidWorldException {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new InvalidWorldException(type + " on seprate worlds");
		}
		this.maxx = AreaAPI.getMaxX(l1, l2);
		this.maxz = AreaAPI.getMaxZ(l1, l2);
		this.minx = AreaAPI.getMinX(l1, l2);
		this.minz = AreaAPI.getMinZ(l1, l2);
		this.world = l1.getWorld();
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
	public HeightlessArea(Player p1, Player p2) throws InvalidWorldException {
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
				&& loc.getZ() <= maxz && loc.getX() >= minx && loc.getZ() >= minz
				: false;
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
		if (d.equals(Direction.ALL) || d.equals(Direction.UP)
				|| d.equals(Direction.DOWN)) {
			throw new InvalidDirectionException("Cannot expand in direction "
					+ d.toString());
		} else if (d.equals(Direction.SIDES)) {
			this.maxz += amount;
			this.maxx += amount;
			this.minx -= amount;
			this.minz -= amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz -= amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz += amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx += amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx -= amount;
		}
	}

	/**
	 * Fetches the blocks within the area.
	 * 
	 * @return The blocks within the area.
	 */
	public Block[] getBlocks() {
		ArrayList<Block> tileList = new ArrayList<Block>();
		int y = 0;
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
		} while (y <= world.getMaxHeight());
		Block[] tileArray = new Block[tileList.size()];
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
		if (d.equals(Direction.ALL) || d.equals(Direction.UP)
				|| d.equals(Direction.DOWN)) {
			throw new InvalidDirectionException("Cannot expand in direction "
					+ d.toString());
		} else if (d.equals(Direction.SIDES)) {
			this.maxz -= amount;
			this.maxx -= amount;
			this.minx += amount;
			this.minz += amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz += amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz -= amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx -= amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx += amount;
		}
	}

	/**
	 * Fetches the total amount of blocks within the area.
	 * 
	 * @return The total amount of blocks within the area.
	 */
	public int getTotalBlocks() {
		return (int) getLength() * (int) getWidth()
				* (int) world.getMaxHeight();
	}

	@Override
	public void shift(Direction direction, int amount)
			throws InvalidDirectionException {
		// TODO Auto-generated method stub
		
	}

}
