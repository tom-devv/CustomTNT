package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Set;

/**
 * FastExplosion class
 * Custom explosion method which creates a TNT and explodes it immediately
 */
public class FastExplosion implements ExplosionStrategy {

    static String FAST_KEY = "fast";
    private boolean onlyAir = true;
    private Set<Material> nonSolidBlocks = Set.of(Material.AIR, Material.SAND, Material.WATER, Material.LAVA);

    public FastExplosion(boolean onlyAir, Set<Material> nonSolidBlocks){
        this.onlyAir = onlyAir;
        this.nonSolidBlocks = nonSolidBlocks;
    }

    public FastExplosion(boolean onlyAir){
        new FastExplosion(onlyAir, Set.of(Material.AIR, Material.SAND, Material.WATER, Material.LAVA));
    }

    public FastExplosion(){
        new FastExplosion(true);
    }


    public void explode(Location loc){
        Material type = loc.getBlock().getType();
        if ((onlyAir && type != Material.AIR) || !this.nonSolidBlocks.contains(type)) return;
        loc.getWorld().spawnEntity(loc, EntityType.TNT, CreatureSpawnEvent.SpawnReason.CUSTOM, (e -> {
            TNTPrimed primed = (TNTPrimed) e;
            primed.setFuseTicks(0);
            primed.setMetadata(FAST_KEY, new FixedMetadataValue(MoreTNT.getInstance(), true));
        }));
    }

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        explode(tnt.getLocation());
    }
}
