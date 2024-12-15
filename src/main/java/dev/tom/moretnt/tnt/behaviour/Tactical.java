package dev.tom.moretnt.tnt.behaviour;

import de.dustplanet.util.SilkUtil;
import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class Tactical implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        SilkUtil silk = MoreTNT.getSilkUtil();
        if(silk == null) {
            MoreTNT.getInstance().getLogger().severe("SilkSpawners is not installed, cannot handle lucky behaviour\nFix this");
            return;
        }
        List<Block> spawnerBlocks = new ArrayList<>(e.blockList()); // Spawners to be removed
        ConfigurableTNT.Lucky lucky = MoreTNT.getTntSettings().getLucky();
        for (Block block : e.blockList()) { // Use original blockList here

            // Check to prevent thrown errors in SilkUtil
            if(!(block.getState() instanceof CreatureSpawner)) {
                continue;
            }

            String entityType = silk.getSpawnerEntityID(block);

            // Skip non-spawner blocks
            if(!block.getType().equals(Material.SPAWNER) || entityType == null) {
                continue;
            }

            // Block is spawner
            spawnerBlocks.add(block);
        }
        // Only explode spawner blocks
        e.blockList().clear();
        e.blockList().addAll(spawnerBlocks);
    }
}
