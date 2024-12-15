package dev.tom.moretnt.listeners;

import dev.tom.moretnt.tnt.TntHandler;
import dev.tom.moretnt.tnt.TntType;
import dev.tom.moretnt.tnt.behaviour.Hex;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TntExplosionListeners implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        if(!(e.getEntity() instanceof TNTPrimed primed)) return;
        Hex.hexExplode(e); // Call hex explode for EVERY tnt
        TntType type = TntHandler.get(primed);
        if (type == null) return;
        type.getExplosionStrategy().explode(primed, e);
    }

}
