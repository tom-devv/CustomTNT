package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Silent implements ExplosionStrategy {

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Silent silent = MoreTNT.getTntSettings().getSilent();
        e.setCancelled(true);
        e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, e.getLocation(), 1);
        for (Block block : e.blockList()) {
            block.breakNaturally();
        }
    }
}
