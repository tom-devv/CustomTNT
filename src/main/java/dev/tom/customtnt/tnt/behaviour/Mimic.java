package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Mimic implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Mimic mimic = CustomTNT.getTntSettings().getMimic();
        final Location l = tnt.getLocation();
        FastExplosion fast = new FastExplosion();

        long delay = mimic.delayBetweenExplosions();
        for (int i = 0; i < mimic.numberOfExplosions(); i++) {
            Bukkit.getScheduler().runTaskLater(CustomTNT.getInstance(), () -> {
                fast.explode(l);
                effect(l);
            }, delay);
            delay += mimic.delayBetweenExplosions();
        }
    }

    private void effect(Location location){
        location.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, location, 30);
    }
}
