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
        SilkUtil silk = SilkUtil.hookIntoSilkSpanwers();
        if(silk == null) {
            System.out.println("SilkUtil is null");
            return;
        }
        List<Block> clonedBlocks = new ArrayList<>(e.blockList()); // List of blocks to be force removed
        e.blockList().clear(); // Clear original block list
        ConfigurableTNT.Lucky lucky = CustomTNT.getTntSettings().getLucky();
        for (Block block : e.blockList()) {

            String entityType = silk.getSpawnerEntityID(block);
            // Add non-spawner blocks back to the list
            if(entityType == null) {
                System.out.println("Block is not a spawner");
                clonedBlocks.add(block);
                continue;
            }

            System.out.println("EntityID is " + entityType);

            double rand = Math.random();
            if(rand > lucky.dropChance()) continue; // dropChance = 0 -> always skip block
            System.out.println("Rand: " + rand + " chance: " + lucky.dropChance());
            // Drop the spawner
            ItemStack spawner = silk.newSpawnerItem(entityType, parseName(lucky.droppedSpawnerName(), entityType), 1, false);
            block.getWorld().dropItem(block.getLocation(), spawner);

            block.setBlockData(Material.AIR.createBlockData(), true);
        }
        e.blockList().addAll(clonedBlocks); // Explode the non-spawner blocks after handling lucky
    }


    private String parseName(String name, String entityName){
        MiniMessage mm = MiniMessage.miniMessage();
        name.replace("%name%", entityName);
        return mm.deserialize(name).toString();
    }


}
