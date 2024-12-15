package dev.tom.moretnt.listeners;

import dev.tom.moretnt.tnt.TntBuilder;
import dev.tom.moretnt.tnt.TntHandler;
import dev.tom.moretnt.tnt.TntType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TntPlaceListeners implements Listener {

    @EventHandler(ignoreCancelled = true)
    /**
     * Set the tnt PDC data when placed
     */
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        TntType type = TntBuilder.itemToTntType(item);
        if(type == null) return;
        TntHandler.set(e.getBlock(), type);
    }

    @EventHandler(ignoreCancelled = true)
    /**
     * Remove the tnt PDC data when broken
     */
    public void onBlockBreak(BlockBreakEvent e) {
        TntType type = TntHandler.get(e.getBlock());
        if(type == null) return;
        TntHandler.wipe(e.getBlock());
    }

    /**
     * Wipe blocks PDC when pistons extend/retract
     *
     * This is only for dupe prevention
     * Know that block PDC data is location based and NOT block placed
     * so we must wipe it whenever a block is moved/removed/ignited
     *
     * This does clear placed custom tnt data but users won't
     * be placing this tnt a lot, let alone moving it
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockPiston(BlockPistonExtendEvent e) {
       wipeBlocks(e.getBlocks());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPiston(BlockPistonRetractEvent e) {
        wipeBlocks(e.getBlocks());
    }

    private void wipeBlocks(List<Block> blocks){
        blocks.forEach(TntHandler::wipe);
    }





}
