package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Chemical implements ExplosionStrategy{
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Chemical chemical = CustomTNT.getTntSettings().getChemical();
        int r = chemical.nearbyRadius();

        if(chemical.poisonCloudEffect()){
            spawnPoisonCloud(tnt.getLocation(),chemical.cloudParticle(), r, chemical.cloudParticles(), chemical.cloudDuration());
        }
        for (Entity near : tnt.getNearbyEntities(r, r, r)) {
            if(!(near instanceof Player player)) continue;
            player.addPotionEffects(chemical.potions());
            Util.sendParsed(player, chemical.hitWithPotionMessage());
        }
    }


    // Method to create the poison cloud effect
    public static void spawnPoisonCloud(Location location, Particle particle, int radius, int particleCount, long duration) {
        World world = location.getWorld();
        if (world == null) return;

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count > duration) {
                    this.cancel();
                    return;
                }

                for (int i = 0; i < particleCount; i++) {
                    double x = (Math.random() * 2 - 1) * radius;
                    double y = (Math.random() * 2 - 1) * radius;
                    double z = (Math.random() * 2 - 1) * radius;

                    Location particleLocation = location.clone().add(x, y, z);
                    world.spawnParticle(particle, particleLocation, 1, 0.2, 0.2, 0.2, 0); // Green particle
                }

                count++;
            }
        }.runTaskTimer(CustomTNT.getInstance(), 0, 2);
    }
}
