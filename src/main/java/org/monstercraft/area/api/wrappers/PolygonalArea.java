package org.monstercraft.area.api.wrappers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PolygonalArea {

	private Location[] locations;

	/**
	 * Creates a Polygonal area with the blocks specified.
	 * 
	 * @param players
	 *            The blocks to create the area at.
	 */
	public PolygonalArea(final Block... blocks) {
		Location[] locations = new Location[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			locations[i] = blocks[i].getLocation();
		}
		this.locations = locations;
	}

	/**
	 * Creates a Polygonal area with the locations specified.
	 * 
	 * @param players
	 *            The locations to create the area at.
	 */
	public PolygonalArea(final Location... locations) {
		this.locations = locations;
	}

	/**
	 * Creates a Polygonal area with the players specified.
	 * 
	 * @param players
	 *            The players to create the area at.
	 */
	public PolygonalArea(final Player... players) {
		Location[] locations = new Location[players.length];
		for (int i = 0; i < players.length; i++) {
			locations[i] = players[i].getLocation();
		}
		this.locations = locations;
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
	public boolean contains(final Location location) {
		for (final Location check : locations) {
			if (location.equals(check)) {
				return true;
			}
		}
		return false;
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
	 * Fetches the blocks within the area.
	 * 
	 * @return The blocks within the area.
	 */
	public Block[] getBlocks() {
		Block[] blocks = new Block[locations.length];
		for (int i = 0; i < locations.length; i++) {
			blocks[i] = locations[i].getBlock();
		}
		return blocks;
	}

}
