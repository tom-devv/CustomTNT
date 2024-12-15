package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
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
        ConfigurableTNT.Hex hex = MoreTNT.getTntSettings().getHex();
        for (Block b : e.blockList()) {
            if(b.getType() != Material.AIR && b.getType() != Material.SPAWNER) {
                setHexData(b);

                // Spawn particles
                b.getWorld().spawnParticle(hex.hexParticle(), b.getLocation(), hex.hexParticleCount(), hex.particleOffsetX(), hex.particleOffsetY(), hex.particleOffsetZ());
            }
        }
        // Wipe the metadata after 30 seconds
        Bukkit.getScheduler().runTaskLater(MoreTNT.getInstance(), () -> {
            e.blockList().forEach(b -> {
                b.removeMetadata(HEX_KEY, MoreTNT.getInstance());
            });
        }, hex.hexBlockExpiryTime());
    }

    /**
     * Set the hex data to the block.
     * Block metadata is ok here as this value doesn't need to persist.
     * @param block
     */
    private void setHexData(Block block){
        block.setMetadata(HEX_KEY, new FixedMetadataValue(MoreTNT.getInstance(), true));
    }

    /**
    * Call a fast explosion on hexed blocks
     */
    public static void hexExplode(EntityExplodeEvent e){
        ConfigurableTNT.Hex hex = MoreTNT.getTntSettings().getHex();
        if(e.getEntity().hasMetadata(FastExplosion.FAST_KEY)) return; // Ignore fast explosions
        if(e.blockList().isEmpty()) {
            return;
        }
        List<Block> hexedBlocks = e.blockList().stream().filter(b -> b.hasMetadata(HEX_KEY)).toList(); // Only loop hexed blocks
        int c = 0;
        for (Block b : hexedBlocks) {
            // No need to check expiry time as this is only called when the block is still hexed
            // i.e. the runnable has not yet removed the hexed metadata
            boolean isHexed = b.hasMetadata(HEX_KEY);
            if(isHexed){

                // This is from the original code
                // I don't know why it's here
                int i = c;
                c++;
                if(i > 3) return;

                // Inflict double damage by spawning an extra explosion
                Bukkit.getScheduler().runTaskLater(MoreTNT.getInstance(), () -> {
                    FastExplosion fast = new FastExplosion();
                    fast.explode(b.getLocation());
                }, 2 + new Random().nextInt(hex.maxRandomTicksUntilBlockExplodes())); // I also don't know why there's a delay
            }
        }


    }
}
