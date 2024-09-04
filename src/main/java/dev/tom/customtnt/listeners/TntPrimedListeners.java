package dev.tom.customtnt.listeners;

import dev.tom.customtnt.tnt.TntHandler;
import dev.tom.customtnt.tnt.TntBuilder;
import dev.tom.customtnt.tnt.TntType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TntPrimedListeners implements Listener {


    @EventHandler(ignoreCancelled = true)
    /**
     * Set the TNT's PDC data when dispensed
     */
    public void onBlockDispense(BlockDispenseEvent e) {
        ItemStack dispensed = e.getItem();
        TntType type = TntBuilder.itemToTntType(dispensed);
        if(type == null) return;
        e.setCancelled(true);
        mimicTntDispense(e.getBlock(), type);
    }


    @EventHandler(ignoreCancelled = true)
    /**
     * Set the TNT's PDC data when primed from a block
     */
    public void onTNTPrime(TNTPrimeEvent e) {
        TntType type = TntHandler.get(e.getBlock());

        if(type == null) return;
        e.getBlock().setBlockData(Material.AIR.createBlockData());
        e.setCancelled(true);
        TNTPrimed primed = e.getBlock().getWorld().spawn(e.getBlock().getLocation().clone().add(0.5,0,0.5), TNTPrimed.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        TntHandler.set(primed, type);
    }


    /**
     * Mimic the TNT dispense event and set PDC data
     * @param block
     * @param type
     */
    private void mimicTntDispense(@NotNull Block block, @NotNull TntType type){

        Dispenser dispenser =  (Dispenser) block.getBlockData();

        // Spawn the TNT entity at the face of the dispenser
        Location spawnLocation = block.getLocation().clone().add(0.5,0,0.5).add(dispenser.getFacing().getDirection());
        TNTPrimed primed = block.getWorld().spawn(
                spawnLocation,
                TNTPrimed.class,
                CreatureSpawnEvent.SpawnReason.CUSTOM
        );

        TntHandler.set(primed, type);


    }


}
