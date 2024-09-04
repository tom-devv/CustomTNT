package dev.tom.customtnt.listeners;

import dev.tom.customtnt.tnt.TntHandler;
import dev.tom.customtnt.tnt.TntBuilder;
import dev.tom.customtnt.tnt.TntType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
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
        if(!(e.getPrimingEntity() instanceof TNTPrimed primed)) return;
        TntType type = TntHandler.get(e.getBlock());
        if(type == null) return;
        TntHandler.set(primed, type);
    }


    /**
     * Mimic the TNT dispense event and set PDC data
     * @param block
     * @param type
     */
    private void mimicTntDispense(@NotNull Block block, @NotNull TntType type){
        if(!(block.getState() instanceof Directional directional)) return;

        // Spawn the TNT entity at the face of the dispenser
        Location spawnLocation = block.getLocation().clone().add(directional.getFacing().getDirection());

        TNTPrimed primed = block.getWorld().spawn(
                spawnLocation,
                TNTPrimed.class,
                CreatureSpawnEvent.SpawnReason.CUSTOM
        );

        TntHandler.set(primed, type);


    }


}
