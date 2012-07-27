package org.monstercraft.area.api.wrappers;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.monstercraft.area.AreaAPI;
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;
import org.monstercraft.area.api.exception.InvalidPlaneException;
import org.monstercraft.area.api.exception.InvalidWorldException;

public class SinglePlaneArea {

	private double maxx;

	private double minx;

	private double maxz;

	private double minz;

	private int plane;

	private World world;

	/**
	 * Creates a single plane area using 2 blocks locations.
	 * 
	 * @param b1
	 *            The first block.
	 * @param b2
	 *            The second block.
	 * @throws InvalidPlaneException
	 *             Thrown when players are on seprate planes.
	 * @throws InvalidWorldException
	 *             Thrown when players are in different worlds.
	 */
	public SinglePlaneArea(Block b1, Block b2) throws InvalidPlaneException,
			InvalidWorldException {
		this(b1.getLocation(), b2.getLocation(), "Blocks");
	}

	/**
	 * Creates a single plane area using 2 locations.
	 * 
	 * @param l1
	 *            The first location.
	 * @param l2
	 *            The second location.
	 * @throws InvalidPlaneException
	 *             Thrown when players are on seprate planes.
	 * @throws InvalidWorldException
	 *             Thrown when players are in different worlds.
	 */
	public SinglePlaneArea(Location l1, Location l2)
			throws InvalidPlaneException, InvalidWorldException {
		this(l1, l2, "Locations");
	}

	private SinglePlaneArea(Location l1, Location l2, String type)
			throws InvalidPlaneException, InvalidWorldException {
		if ((int) l1.getY() != (int) l2.getY()) {
			throw new InvalidPlaneException(type + " on seprate planes");
		}
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new InvalidWorldException(type + " on seprate worlds");
		}
		this.maxx = AreaAPI.getMaxX(l1, l2);
		this.maxz = AreaAPI.getMaxZ(l1, l2);
		this.minx = AreaAPI.getMinX(l1, l2);
		this.minz = AreaAPI.getMinZ(l1, l2);
		this.plane = (int) l1.getY();
		this.world = l1.getWorld();
	}

	/**
	 * Creates a single plane area using 2 players locations.
	 * 
	 * @param p1
	 *            The first player.
	 * @param p2
	 *            The second player.
	 * @throws InvalidPlaneException
	 *             Thrown when players are on seprate planes.
	 * @throws InvalidWorldException
	 *             Thrown when players are in different worlds.
	 */
	public SinglePlaneArea(Player p1, Player p2) throws InvalidPlaneException,
			InvalidWorldException {
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
		return ((int) loc.getY() == plane && loc.getWorld().equals(world)) ? loc
				.getX() < maxx
				&& loc.getZ() < maxz
				&& loc.getX() > minx
				&& loc.getZ() > minz : false;
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
			this.maxz = maxz + amount;
			this.maxx = maxx + amount;
			this.minx = minx - amount;
			this.minz = minz - amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz = minz - amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz = maxz + amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx = maxx + amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx = minx - amount;
		}
	}

	/**
	 * Fetches the blocks within the area.
	 * 
	 * @return The blocks within the area.
	 */
	public Block[] getBlocks() {
		ArrayList<Block> tileList = new ArrayList<Block>();
		int x = (int) getMinX();
		do {
			int z = (int) getMinZ();
			do {
				if (this.contains(world.getBlockAt(x, plane, z))) {
					tileList.add(world.getBlockAt(x, plane, z));
				}
				z++;
			} while (z <= getMaxZ());
			x++;
		} while (x <= getMaxX());
		Block[] tileArray = new Block[tileList.size()];
		int j = 0;
		while (j < tileList.size()) {
			tileArray[j] = tileList.get(j);
			j++;
		}
		return tileArray;
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
	 * The plane the area is on.
	 * 
	 * @return The plane the ares is on.
	 */
	public double getPlane() {
		return plane;
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
			this.maxz = maxz - amount;
			this.maxx = maxx - amount;
			this.minx = minx + amount;
			this.minz = minz + amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz = minz + amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz = maxz - amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx = maxx - amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx = minx + amount;
		}
	}
}
