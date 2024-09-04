package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Huge implements ExplosionStrategy{
    @Override
    public void explode(TNTPrimed tnt) {
        ConfigurableTNT.Huge huge = CustomTNT.getTntSettings().getHuge();
        FastExplosion explosion = new FastExplosion();
        // Guaranteed explosions
        for (Vector vec : huge.guaranteedExplosionVectors()) {
            explosion.explode(tnt.getLocation().clone().add(vec));
        }
        // Random explosions
        Set<Vector> chosen = ThreadLocalRandom.current().nextBoolean() ? huge.alternativeExplosionVectors() : huge.possibleExplosionsVectors();
    }
}
