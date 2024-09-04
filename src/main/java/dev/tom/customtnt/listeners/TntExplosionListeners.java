package dev.tom.customtnt.listeners;

import dev.tom.customtnt.tnt.TntHandler;
import dev.tom.customtnt.tnt.TntType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TntExplosionListeners implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        if(!(e.getEntity() instanceof TNTPrimed primed)) return;
        TntType type = TntHandler.get(primed);
        if (type == null) return;
        type.getExplosionStrategy().explode(primed);
    }

}
