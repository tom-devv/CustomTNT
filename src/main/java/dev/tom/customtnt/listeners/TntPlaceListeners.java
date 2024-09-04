package dev.tom.customtnt.listeners;

import dev.tom.customtnt.tnt.TntBuilder;
import dev.tom.customtnt.tnt.TntHandler;
import dev.tom.customtnt.tnt.TntType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class TntPlaceListeners implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        TntType type = TntBuilder.itemToTntType(item);
        if(type == null) return;
        TntHandler.set(e.getBlock(), type);
    }

}
