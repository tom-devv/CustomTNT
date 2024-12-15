package dev.tom.moretnt.tnt;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import dev.tom.moretnt.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.persistence.PersistentDataType;

public class TntHandler {

    /**
     * Set the type of TNT inside the entity PDC
     * @param primed the primed tnt
     * @param type the type of tnt
     */
    public static void set(TNTPrimed primed, TntType type){
        primed.getPersistentDataContainer().set(TntBuilder.TNT_ITEM_KEY, PersistentDataType.STRING,type.name());
    }

    /**
     * Utility method to store the TntType "inside" the block
     * Note: This uses nbtapi for chunk pdc getting/setting
     * @param block the block
     * @param type the type of tnt
     */
    public static void set(Block block, TntType type){
        NBTBlock nbtBlock = new NBTBlock(block);
        nbtBlock.getData().setString(TntBuilder.NBT_KEY, type.name());
    }

    /**
     * Get the type of TNT from the block PDC
     * @param block
     * @return the type of tnt
     */
    public static TntType get(Block block){
        NBTBlock nbtBlock = new NBTBlock(block);
        String value = nbtBlock.getData().getString(TntBuilder.NBT_KEY);
        TntType type = TntType.fromString(value);
        if(value != null) {
            return type;
        } else {
            return null;
        }
    }


    /**
     * Get the type of TNT from the entity PDC
     * @param entity the entity
     * @return the type of tnt
     */
    public static TntType get(Entity entity) {
        String value = entity.getPersistentDataContainer().get(TntBuilder.TNT_ITEM_KEY, PersistentDataType.STRING);
        if(Util.isValidEnum(TntType.class, value)) {
            return TntType.valueOf(value);
        } else {
            return null;
        }
    }

    public static void wipe(Block block){
        if(!block.getType().equals(Material.TNT)) return;
        NBTBlock nbtBlock = new NBTBlock(block);
        nbtBlock.getData().removeKey(TntBuilder.NBT_KEY);
    }




}
