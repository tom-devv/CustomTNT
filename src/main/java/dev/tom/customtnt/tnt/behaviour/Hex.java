package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Random;

public class Hex implements ExplosionStrategy {

    static String HEX_KEY = "HEX";

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Hex hex = CustomTNT.getTntSettings().getHex();
        for (Block b : e.blockList()) {
            if(b.getType() != Material.AIR && b.getType() != Material.SPAWNER) {
                setHexData(b);

                // Spawn particles
                b.getWorld().spawnParticle(hex.hexParticle(), b.getLocation(), hex.hexParticleCount(), hex.particleOffsetX(), hex.particleOffsetY(), hex.particleOffsetZ());
            }
        }
        // Wipe the metadata after 30 seconds
        Bukkit.getScheduler().runTaskLater(CustomTNT.getInstance(), () -> {
            e.blockList().forEach(b -> {
                b.removeMetadata(HEX_KEY, CustomTNT.getInstance());
            });
        }, hex.hexBlockExpiryTime());
    }

    /**
     * Set the hex data to the block.
     * Block metadata is ok here as this value doesn't need to persist.
     * @param block
     */
    private void setHexData(Block block){
        block.setMetadata(HEX_KEY, new FixedMetadataValue(CustomTNT.getInstance(), Bukkit.getCurrentTick()));
    }

    public static void hexExplode(EntityExplodeEvent e){
        ConfigurableTNT.Hex hex = CustomTNT.getTntSettings().getHex();
        if(e.getEntity().hasMetadata(FastExplosion.FAST_KEY)) return; // Ignore fast explosions
        List<Block> hexedBlocks = e.blockList().stream().filter(b -> b.hasMetadata(HEX_KEY)).toList(); // Only loop hexed blocks
        if(e.blockList().isEmpty()) return;
        int c = 0;
        for (Block b : hexedBlocks) {
            long tickDelta = Bukkit.getCurrentTick() - b.getMetadata(HEX_KEY).getFirst().asLong();
            if(tickDelta < hex.timeUntilBlockCanExplode()){
                int i = c;
                c++;
                if(i > 3){
                    return;
                } else {
                    Bukkit.getScheduler().runTaskLater(CustomTNT.getInstance(), () -> {
                        FastExplosion fast = new FastExplosion();
                        fast.explode(b.getLocation());
                    }, 2 + new Random().nextInt(hex.maxRandomTicksUntilBlockExplodes()));
                }
            }
        }


    }
}
