package dev.tom.customtnt.tnt;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.files.ConfigurableTNT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;


public class TntBuilder {

    public static Map<String, ItemStack> tntItems = new HashMap<>();
    public static String NBT_KEY = "custom-tnt";
    public static NamespacedKey TNT_ITEM_KEY;

    public TntBuilder(CustomTNT plugin) {
        TNT_ITEM_KEY = new NamespacedKey(plugin, NBT_KEY);
    }

    /**
     * Only build all TNT items once and get from map later
     */
    public void buildAllItems(){
        List<String> enabledTNTTypes = CustomTNT.getTntSettings().getEnabledTNTTypes().stream().map(String::toUpperCase).toList();
        for (TntType type : TntType.values()) {
            boolean enabled = enabledTNTTypes.contains(type.name().toUpperCase());
            if(!enabled){
                CustomTNT.getInstance().getLogger().severe("TNT type " + type.name() + " is not enabled in the config");
                continue;
            }
            ItemStack item = toItemStack(type);
            if(item == null) continue;
            tntItems.put(type.name(), item);
        }
    }

    /**
     * Create an ItemStack for the given TNT type
     * @param type
     * @return
     */
    private ItemStack toItemStack(TntType type){

        // Get the TNT item settings from the config
        ConfigurableTNT.TntItem tntItem = CustomTNT.getTntSettings().getTnts().stream()
                .filter(tnt -> tnt.name().equalsIgnoreCase(type.name())).findFirst().orElse(null);
        if(tntItem == null) return null;

        MiniMessage mm = MiniMessage.miniMessage();

        ItemStack item = new ItemStack(tntItem.material(), 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(mm.deserialize(tntItem.name()));

        // Deserialize lore from config
        List<Component> lore = new ArrayList<>();
        for (String s : tntItem.lore()) {
            lore.add(mm.deserialize(s));
        }
        meta.lore(lore);
        // Set PDC for type retrieval later
        meta.getPersistentDataContainer().set(TNT_ITEM_KEY, PersistentDataType.STRING, type.name());
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Utility to retrieve a TnType from an ItemStack
      * @param item
     * @return the TntType of the ItemStack
     */
    public static TntType itemToTntType(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        String value = meta.getPersistentDataContainer().get(TNT_ITEM_KEY, PersistentDataType.STRING);
        return Util.isValidEnum(TntType.class, value) ? TntType.valueOf(value) : null;
    }



}
