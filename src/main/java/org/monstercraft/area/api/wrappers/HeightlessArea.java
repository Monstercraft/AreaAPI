package org.monstercraft.area.api.wrappers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.monstercraft.area.AreaAPI;
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;
import org.monstercraft.area.api.exception.InvalidWorldException;

public class HeightlessArea {

	private double maxx;

	private double minx;

	private double maxz;

	private double minz;

	private World world;

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

	public HeightlessArea(Location l1, Location l2)
			throws InvalidWorldException {
		this(l1, l2, "Locations");
	}

	public HeightlessArea(Block b1, Block b2) throws InvalidWorldException {
		this(b1.getLocation(), b2.getLocation(), "Blocks");
	}

	public HeightlessArea(Player p1, Player p2) throws InvalidWorldException {
		this(p1.getLocation(), p2.getLocation(), "Players");
	}

	public double getMaxX() {
		return maxx;
	}

	public double getMaxZ() {
		return maxz;
	}

	public double getMinX() {
		return minx;
	}

	public double getMinZ() {
		return minz;
	}

	public World getWorld() {
		return world;
	}

	public double getWidth() {
		return maxz - minz;
	}

	public double getLength() {
		return maxx - minx;
	}

	public boolean contains(Block block) {
		return contains(block.getLocation());
	}

	public boolean contains(Player player) {
		return contains(player.getLocation());
	}

	public boolean contains(Location loc) {
		return loc.getWorld().equals(world) ? loc.getX() < maxx
				&& loc.getZ() < maxz && loc.getX() > minx && loc.getZ() > minz
				: false;
	}

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
