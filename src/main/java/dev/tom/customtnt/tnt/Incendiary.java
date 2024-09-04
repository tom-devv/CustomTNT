package dev.tom.customtnt.tnt;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class Incendiary implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt) {
       ConfigurableTNT.Incendiary settings = CustomTNT.getTntSettings().getIncendiary();
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
