package dev.tom.customtnt.files;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
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
            new TntItem("Huge", "<rainbow>Huge TNT</rainbow>", List.of("<red>A huge explosion</red>"), Material.TNT),
            new TntItem("Chemical", "<rainbow>Chemical TNT</rainbow>", List.of("<red>A chemical explosion</red>"), Material.TNT),
            new TntItem("Lethal", "<rainbow>Lethal TNT</rainbow>", List.of("<red>A lethal explosion</red>"), Material.TNT),
            new TntItem("Incendiary", "<rainbow>Incendiary TNT</rainbow>", List.of("<red>An incendiary explosion</red>"), Material.TNT),
            new TntItem("Lucky", "<rainbow>Lucky TNT</rainbow>", List.of("<red>A lucky explosion</red>"), Material.TNT),
            new TntItem("Tactical", "<rainbow>Tactical TNT</rainbow>", List.of("<red>A tactical explosion</red>"), Material.TNT),
            new TntItem("Mimic", "<rainbow>Mimic TNT</rainbow>", List.of("<red>A mimic explosion</red>"), Material.TNT),
            new TntItem("Hex", "<rainbow>Hex TNT</rainbow>", List.of("<red>A hex explosion</red>"), Material.TNT),
            new TntItem("Silent", "<rainbow>Silent TNT</rainbow>", List.of("<red>A silent explosion</red>"), Material.TNT)
    );

    // Configuration record(s) for Custom TNT types
    public record Incendiary(int flameCount, int nearbyRadius, int fireTicks, List<String> onFireMessage, int onFireFlameCount){}
    @Comment({"Configuration options for custom TNT types"}) // Comment here as this is top of list
    Incendiary incendiary = new Incendiary(50, 8, 180, List.of("<red>You are on fire by incendiary TNT!!!</red>"), 12);

    public record Chemical(int nearbyRadius, Set<PotionEffect> potions, List<String> hitWithPotionMessage, boolean poisonCloudEffect, Particle cloudParticle, long cloudDuration, int cloudParticles){}
    @Comment({"Cloud-duration is in ticks (20 ticks = 1 second)" +
            "Nearby radius is the radius around the TNT where the potion effects will be applied" +
            "The cloud particles are the number of particles to spawn in the cloud" +
            "The cloud particle is the particle type to spawn in the cloud, the cloud is a visual representation of the effect" +
            "of the chemical tnt that just exploded, all players within the radius (defined by nearby-radius) will be hit with the potion effects"})
    Chemical chemical = new Chemical(8, Set.of(
            new PotionEffect(PotionEffectType.POISON, 100, 1),
            new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)
    ), List.of("<red>You have been hit with a chemical TNT!!!</red>"), true, Particle.SNEEZE, 50, 100);

    public record Lethal(int fastExplosionsCount){}
    Lethal lethal = new Lethal(5);

    public record Huge(Set<Vector> guaranteedExplosionVectors, Set<Vector> possibleExplosionsVectors, Set<Vector> alternativeExplosionVectors){}
    @Comment({"If you're confused by this, don't worry I was too! It seems as if we should randomly choose"
            + "between the possibleExplosionsVectors and alternativeExplosionVectors, but I'm not sure why!"
            + "The default values are the ones that were used originally (Note: only one set of the two possibilities is chosen)"
    })
    Huge huge = new Huge(
            Set.of(
            new Vector(0, 3, 0),
            new Vector(0, -3, 0)
            ),
            Set.of(
                new Vector(3, 0, 0),
                new Vector(0,0,3)
            ),
            Set.of(
                new Vector(-3,0,0),
                new Vector(0,0,-3)
            )
    );

    public record Hex(long hexBlockExpiryTime, int maxRandomTicksUntilBlockExplodes, int hexParticleCount, Particle hexParticle, double particleOffsetX, double particleOffsetY, double particleOffsetZ){}
    @Comment({"Expiry time and time to explode are in ticks (20 ticks = 1 second)" +
            "The random ticks until block explodes is the max number of ticks to wait (2 + this number) after the hex block was" +
            "hit with an explosion until it explodes" })
    Hex hex = new Hex(600, 8,10, Particle.HAPPY_VILLAGER, 0.5, 0.5, 0.5);

    public record Silent(boolean nothing){}
    @Comment({"No configuration options for Silent TNT yet, the value below is just a placeholder"})
    Silent silent = new Silent(true);

    public record Mimic(int numberOfExplosions, long delayBetweenExplosions){}
    @Comment({"Delay between fake explosions is in ticks (20 ticks = 1 second)"})
    Mimic mimic = new Mimic(3, 100);

    public Mimic getMimic() {
        return mimic;
    }

    public Silent getSilent() {
        return silent;
    }

    public Hex getHex() {
        return hex;
    }

    public Huge getHuge() {
        return huge;
    }

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
