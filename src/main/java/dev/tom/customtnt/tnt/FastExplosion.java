package dev.tom.customtnt.tnt;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Set;

public class FastExplosion implements ExplosionStrategy {

    private int count = 1;
    private boolean onlyAir;
    private Set<Material> nonSolidBlocks;

    public FastExplosion(boolean onlyAir, Set<Material> nonSolidBlocks, int count){
        this.onlyAir = onlyAir;
        this.nonSolidBlocks = nonSolidBlocks;
        this.count = count;
    }

    public FastExplosion(boolean onlyAir, int count){
        new FastExplosion(onlyAir, Set.of(Material.AIR, Material.SAND, Material.WATER, Material.LAVA), count);
    }

    public FastExplosion(int count){
        new FastExplosion(true, count);
    }


    @Override
    public void explode(TNTPrimed tnt) {
        Material type = tnt.getLocation().getBlock().getType();
        if((onlyAir && type != Material.AIR) || !this.nonSolidBlocks.contains(type)) return;
        for (int i = 0; i < count; i++) {
            tnt.getWorld().spawnEntity(tnt.getLocation(), EntityType.TNT, CreatureSpawnEvent.SpawnReason.CUSTOM, (e -> {
                TNTPrimed primed = (TNTPrimed) e;
                primed.setFuseTicks(0);
            }));
        }

    }
}
