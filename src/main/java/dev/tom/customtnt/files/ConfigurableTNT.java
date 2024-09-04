package dev.tom.customtnt.files;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to configure the TNT types that are enabled in the plugin.
 */
@Configuration
public class ConfigurableTNT {

    public ConfigurableTNT() {};


    /**
     * Configuration record for TNT ItemStack properties
     * @param type
     * @param name
     * @param lore
     */
    public record TntItem(String type, String name, List<String> lore, Material material){}


    /**
     * Configuration class for TNT settings
     * such as enabled TNT types and their ItemStack props
     */
    @Comment({"List of enabled TNT types"})
    private Set<String> enabledTNTTypes = Set.of("Huge", "Chemical", "Lethal", "Incendiary", "Lucky", "Tactical", "Mimic", "Hex", "Silent");
    @Comment({"Configure all the TNT ItemStacks below, even if they are not enabled"})
    Set<TntItem> tnts = Set.of(
            new TntItem("Huge", "Huge TNT", List.of("A huge explosion"), Material.TNT),
            new TntItem("Chemical", "Chemical TNT", List.of("A chemical explosion"), Material.TNT),
            new TntItem("Lethal", "Lethal TNT", List.of("A lethal explosion"), Material.TNT),
            new TntItem("Incendiary", "Incendiary TNT", List.of("An incendiary explosion"), Material.TNT),
            new TntItem("Lucky", "Lucky TNT", List.of("A lucky explosion"), Material.TNT),
            new TntItem("Tactical", "Tactical TNT", List.of("A tactical explosion"), Material.TNT),
            new TntItem("Mimic", "Mimic TNT", List.of("A mimic explosion"), Material.TNT),
            new TntItem("Hex", "Hex TNT", List.of("A hex explosion"), Material.TNT),
            new TntItem("Silent", "Silent TNT", List.of("A silent explosion"), Material.TNT)
    );

    // Configuration record(s) for Custom TNT types
    public record Incendiary(int flameCount, int nearbyRadius, int fireTicks, List<String> onFireMessage, int onFireFlameCount){}
    @Comment({"Configuration options for custom TNT types"}) // Comment here as this is top of list
    Incendiary incendiary = new Incendiary(50, 8, 180, List.of("<red>You are on fire by incendiary TNT!!!</red>"), 12);

    public record Chemical(int nearbyRadius, Set<PotionEffect> potions, List<String> hitWithPotionMessage){}
    Chemical chemical = new Chemical(8, Set.of(
            new PotionEffect(PotionEffectType.POISON, 100, 1),
            new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)
    ), List.of("<red>You have been hit with a chemical TNT!!!</red>"));

    public record Lethal(int fastExplosionsCount){}
    Lethal lethal = new Lethal(5);


    public Lethal getLethal() {
        return lethal;
    }

    public Chemical getChemical() {
        return chemical;
    }

    public Incendiary getIncendiary() {
        return incendiary;
    }

    public Set<TntItem> getTnts() {
        return tnts;
    }

    public Set<String> getEnabledTNTTypes() {
        return enabledTNTTypes;
    }
}
