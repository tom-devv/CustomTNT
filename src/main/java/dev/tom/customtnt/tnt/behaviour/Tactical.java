package dev.tom.customtnt.tnt.behaviour;

import de.dustplanet.util.SilkUtil;
import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Tactical implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        SilkUtil silk = CustomTNT.getSilkUtil();
        if(silk == null) {
            CustomTNT.getInstance().getLogger().severe("SilkSpawners is not installed, cannot handle lucky behaviour\nFix this");
            return;
        }
        List<Block> spawnerBlocks = new ArrayList<>(e.blockList()); // Spawners to be removed
        ConfigurableTNT.Lucky lucky = CustomTNT.getTntSettings().getLucky();
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
