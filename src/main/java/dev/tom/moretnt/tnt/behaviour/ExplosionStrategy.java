package dev.tom.moretnt.tnt.behaviour;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Interface representing the strategy pattern for TNT explosions
 */
public interface ExplosionStrategy {

    public void explode(TNTPrimed tnt, EntityExplodeEvent e);

}
