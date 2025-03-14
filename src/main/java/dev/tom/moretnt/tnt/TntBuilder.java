package dev.tom.moretnt.tnt;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;


public class TntBuilder {

    public static Map<String, ItemStack> tntItems = new HashMap<>();
    public static String NBT_KEY = "more-tnt";
    public static NamespacedKey TNT_ITEM_KEY;

    public TntBuilder(MoreTNT plugin) {
        TNT_ITEM_KEY = new NamespacedKey(plugin, NBT_KEY);
    }

    /**
     * Only build all TNT items once and get from map later
     */
    public void buildAllItems(){
        List<String> enabledTNTTypes = MoreTNT.getTntSettings().getEnabledTNTTypes().stream().map(String::toUpperCase).toList();
        for (TntType type : TntType.values()) {
            boolean enabled = enabledTNTTypes.contains(type.name().toUpperCase());
            if(!enabled){
                MoreTNT.getInstance().getLogger().severe("TNT type " + type.name() + " is not enabled in the config");
                continue;
            }
            ItemStack item = toItemStack(type);
            if(item == null) {
                continue;
            }
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
        ConfigurableTNT.TntItem tntItem = MoreTNT.getTntSettings().getTnts().stream()
                .filter(tnt -> tnt.type().equalsIgnoreCase(type.name())).findFirst().orElse(null);
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
        return TntType.fromString(value);
    }

    public static ItemStack getItem(TntType type){
        return tntItems.get(type.name());
    }



}
