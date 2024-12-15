package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.Util;
import dev.tom.moretnt.files.ConfigurableTNT;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Incendiary implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
       ConfigurableTNT.Incendiary settings = MoreTNT.getTntSettings().getIncendiary();
        Location l = tnt.getLocation();
        l.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, settings.flameCount());
        int r = settings.nearbyRadius();
        for (Entity near : tnt.getNearbyEntities(r, r, r)) {
            if(!(near instanceof Player player)) continue;
            player.setFireTicks(settings.fireTicks());
            player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, settings.onFireFlameCount());
            Util.sendParsed(player, settings.onFireMessage());
        }
    }

}
