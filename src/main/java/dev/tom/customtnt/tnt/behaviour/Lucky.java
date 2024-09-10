package dev.tom.customtnt.tnt.behaviour;

import de.dustplanet.util.SilkUtil;
import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Lucky implements ExplosionStrategy {

    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        SilkUtil silk = CustomTNT.getSilkUtil();
        if(silk == null) {
            CustomTNT.getInstance().getLogger().severe("SilkSpawners is not installed, cannot handle lucky behaviour\nFix this");
            return;
        }
        List<Block> clonedBlocks = new ArrayList<>(e.blockList()); // List of blocks to be force removed
        ConfigurableTNT.Lucky lucky = CustomTNT.getTntSettings().getLucky();
        for (Block block : e.blockList()) { // Use original blockList here
            // Check to prevent thrown errors in SilkUtil
            if(!(block.getState() instanceof CreatureSpawner)) {
                clonedBlocks.add(block);
                continue;
            }

            String entityType = silk.getSpawnerEntityID(block);
            // Add non-spawner blocks back to the list
            if(!block.getType().equals(Material.SPAWNER) || entityType == null) {
                clonedBlocks.add(block);
                continue;
            }

            double rand = Math.random();
            if(rand > lucky.dropChance()) continue; // dropChance = 0 -> always skip block

            // Drop the spawner
            ItemStack spawner = silk.newSpawnerItem(entityType, silk.getCustomSpawnerName(entityType), 1, false);
            block.getWorld().dropItem(block.getLocation(), spawner);

            block.setBlockData(Material.AIR.createBlockData(), true);
        }
        e.blockList().clear(); // Clear original block list
        e.blockList().addAll(clonedBlocks); // Explode the non-spawner blocks after handling lucky
    }
}
