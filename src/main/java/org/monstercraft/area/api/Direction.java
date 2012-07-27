package org.monstercraft.area.api;

/**
 * An enum containing all of the possible directions to expand/shrink an area.
 * 
 * @author Fletch_to_99
 * 
 */
public enum Direction {
	/**
	 * The direction up.
	 */
	UP,
	/**
	 * The direction down.
	 */
	DOWN,
	/**
	 * The direction north.
	 */
	NORTH,
	/**
	 * The direction south.
	 */
	SOUTH,
	/**
	 * The direction east.
	 */
	EAST,
	/**
	 * The direction west.
	 */
	WEST,
	/**
	 * Implies all directions (N,S,E,W,Up,Down).
	 */
	ALL,
	/**
	 * Implies all compass directions (N, S, E, W).
	 */
	SIDES
}
