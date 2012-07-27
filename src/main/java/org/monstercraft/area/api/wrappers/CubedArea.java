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

public class CubedArea {

	private double maxx;

	private double minx;

	private double maxy;

	private double miny;

	private double maxz;

	private double minz;

	private World world;

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

	public CubedArea(Location l1, Location l2) throws InvalidWorldException {
		this(l1, l2, "Locations");
	}

	public CubedArea(Block b1, Block b2) throws InvalidWorldException {
		this(b1.getLocation(), b2.getLocation(), "Blocks");
	}

	public CubedArea(Player p1, Player p2) throws InvalidWorldException {
		this(p1.getLocation(), p2.getLocation(), "Players");
	}

	public double getMaxX() {
		return maxx;
	}

	public double getMaxY() {
		return maxy;
	}

	public double getMinX() {
		return minx;
	}

	public double getMinY() {
		return miny;
	}

	public double getMaxZ() {
		return maxz;
	}

	public double getMinZ() {
		return minz;
	}

	public World getWorld() {
		return world;
	}

	public double getHeight() {
		return maxy - miny;
	}

	public double getWidth() {
		return maxz - minz;
	}

	public double getLength() {
		return maxx - minx;
	}

	public boolean contains(Player player) {
		return contains(player.getLocation());
	}

	public boolean contains(Block block) {
		return contains(block.getLocation());
	}

	public boolean contains(Location loc) {
		return loc.getWorld().equals(world) ? loc.getX() < maxx
				&& loc.getY() < maxy && loc.getX() > minx && loc.getY() > miny
				&& loc.getZ() < maxz && loc.getZ() > minz && loc.getZ() < maxz
				: false;
	}

	public void expand(Direction d, int amount)
			throws InvalidDirectionException {
		if (d.equals(Direction.ALL)) {
			this.maxz = maxz + amount;
			this.maxx = maxx + amount;
			this.maxy = maxy + amount;
			this.minx = minx - amount;
			this.minz = minz - amount;
			this.miny = miny - amount;
		} else if (d.equals(Direction.SIDES)) {
			this.maxz = maxz + amount;
			this.maxx = maxx + amount;
			this.minx = minx - amount;
			this.minz = minz - amount;
		} else if (d.equals(Direction.UP)) {
			this.maxy = maxy + amount;
		} else if (d.equals(Direction.DOWN)) {
			this.miny = miny - amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx = minx - amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz = minz - amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz = maxz + amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx = maxx + amount;
		} else {
			throw new InvalidDirectionException("Invalid direction specified");
		}
	}

	public void shrink(Direction d, int amount)
			throws InvalidDirectionException {
		if (d.equals(Direction.ALL)) {
			this.maxz = maxz - amount;
			this.maxx = maxx - amount;
			this.maxy = maxy - amount;
			this.minx = minx + amount;
			this.minz = minz + amount;
			this.miny = miny + amount;
		} else if (d.equals(Direction.SIDES)) {
			this.maxz = maxz - amount;
			this.maxx = maxx - amount;
			this.minx = minx + amount;
			this.minz = minz + amount;
		} else if (d.equals(Direction.UP)) {
			this.maxy = maxy - amount;
		} else if (d.equals(Direction.DOWN)) {
			this.miny = miny + amount;
		} else if (d.equals(Direction.NORTH)) {
			this.minz = minz + amount;
		} else if (d.equals(Direction.SOUTH)) {
			this.maxz = maxz - amount;
		} else if (d.equals(Direction.EAST)) {
			this.maxx = maxx - amount;
		} else if (d.equals(Direction.WEST)) {
			this.minx = minx + amount;
		} else {
			throw new InvalidDirectionException("Invalid direction specified");
		}
	}

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
		int j = 0;
		while (j < tileList.size()) {
			tileArray[j] = tileList.get(j);
			j++;
		}
		return tileArray;
	}
}
