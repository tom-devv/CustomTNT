package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Silent implements ExplosionStrategy {

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Silent silent = CustomTNT.getTntSettings().getSilent();
        e.setCancelled(true);
        e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, e.getLocation(), 1);
        for (Block block : e.blockList()) {
            block.setBlockData(Material.AIR.createBlockData());
        }
    }
}
