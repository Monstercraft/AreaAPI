package org.monstercraft.area.api.wrappers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;
import org.monstercraft.area.api.exception.InvalidWorldException;

public class PolygonalArea extends Area {

    private final Location[] locations;

    private World world;

    /**
     * Creates a Polygonal area with the blocks specified.
     *
     * @param players
     *            The blocks to create the area at.
     */
    public PolygonalArea(final Block... blocks) throws InvalidWorldException {
        final Location[] locations = new Location[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            locations[i] = blocks[i].getLocation();
        }
        final World world = locations[0].getWorld();
        for (final Location l : locations) {
            if (l.getWorld() != world) {
                throw new InvalidWorldException("Blocks on seprate worlds");
            }
        }
        this.locations = locations;
        this.world = world;
    }

    /**
     * Creates a Polygonal area with the locations specified.
     *
     * @param players
     *            The locations to create the area at.
     */
    public PolygonalArea(final Location... locations)
            throws InvalidWorldException {
        final World world = locations[0].getWorld();
        for (final Location l : locations) {
            if (l.getWorld() != world) {
                throw new InvalidWorldException("Locations on seprate worlds");
            }
        }
        this.locations = locations;
        this.world = world;
    }

    /**
     * Creates a Polygonal area with the players specified.
     *
     * @param players
     *            The players to create the area at.
     */
    public PolygonalArea(final Player... players) {
        final Location[] locations = new Location[players.length];
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
    @Override
    public boolean contains(final Player player) {
        return this.contains(player.getLocation());
    }

    @Override
    public void expand(final Direction direction, final int amount)
            throws InvalidDirectionException {
        // TODO Auto-generated method stub

    }

    /**
     * Fetches the blocks within the area.
     *
     * @return The blocks within the area.
     */
    @Override
    public Block[] getBlocks() {
        final Block[] blocks = new Block[locations.length];
        for (int i = 0; i < locations.length; i++) {
            blocks[i] = locations[i].getBlock();
        }
        return blocks;
    }

    /**
     * Fetches the total amount of blocks within the area.
     *
     * @return The total amount of blocks within the area.
     */
    @Override
    public int getTotalBlocks() {
        return this.getBlocks().length;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void shift(final Direction direction, final int amount)
            throws InvalidDirectionException {
        throw new InvalidDirectionException("Cannot shift this area.");
    }

    @Override
    public void shrink(final Direction direction, final int amount)
            throws InvalidDirectionException {
        // TODO Auto-generated method stub

    }

}
