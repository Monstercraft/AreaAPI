package org.monstercraft.area.api.wrappers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.monstercraft.area.api.Direction;
import org.monstercraft.area.api.exception.InvalidDirectionException;

public abstract class Area {

    public boolean contains(final Area area) {
        if (area.getBlocks().length > this.getBlocks().length) {
            return false;
        }
        for (final Block b : area.getBlocks()) {
            if (!this.contains(b)) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean contains(Block block);

    public abstract boolean contains(Location location);

    public abstract boolean contains(Player block);

    public abstract void expand(Direction direction, int amount)
            throws InvalidDirectionException;

    public abstract Block[] getBlocks();

    public abstract int getTotalBlocks();

    public abstract World getWorld();

    public abstract void shift(Direction direction, int amount)
            throws InvalidDirectionException;

    public abstract void shrink(Direction direction, int amount)
            throws InvalidDirectionException;
}
