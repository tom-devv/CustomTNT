package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Chemical implements ExplosionStrategy{
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Chemical chemical = CustomTNT.getTntSettings().getChemical();
        int r = chemical.nearbyRadius();
        for (Entity near : tnt.getNearbyEntities(r, r, r)) {
            if(!(near instanceof Player player)) continue;
            player.addPotionEffects(chemical.potions());
            Util.sendParsed(player, chemical.hitWithPotionMessage());
        }
    }
}
