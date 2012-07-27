package org.monstercraft.area.api.wrappers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PolygonalArea {

	private Location[] locations;

	public PolygonalArea(final Location... locations) {
		this.locations = locations;
	}

	public PolygonalArea(final Block... blocks) {
		Location[] locations = new Location[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			locations[i] = blocks[i].getLocation();
		}
		this.locations = locations;
	}

	public PolygonalArea(final Player... players) {
		Location[] locations = new Location[players.length];
		for (int i = 0; i < players.length; i++) {
			locations[i] = players[i].getLocation();
		}
		this.locations = locations;
	}

	public boolean contains(Player player) {
		return contains(player.getLocation());
	}

	public boolean contains(Block block) {
		return contains(block.getLocation());
	}

	public boolean contains(final Location location) {
		for (final Location check : locations) {
			if (location.equals(check)) {
				return true;
			}
		}
		return false;
	}

	public Block[] getBlocks() {
		Block[] blocks = new Block[locations.length];
		for (int i = 0; i < locations.length; i++) {
			blocks[i] = locations[i].getBlock();
		}
		return blocks;
	}

}
