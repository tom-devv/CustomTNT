package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Silent implements ExplosionStrategy {

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Silent silent = CustomTNT.getTntSettings().getSilent();
        int r = silent.silentRadius();
        for (Entity near : tnt.getNearbyEntities(r, r * 2, r)) {
            if(near instanceof Player player){
//                player.playSound();
            }
        }
    }
}
