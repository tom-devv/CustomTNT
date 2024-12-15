package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Huge implements ExplosionStrategy{
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Huge huge = MoreTNT.getTntSettings().getHuge();
        FastExplosion explosion = new FastExplosion();
        // Guaranteed explosions
        for (Vector vec : huge.guaranteedExplosionVectors()) {
            explosion.explode(tnt.getLocation().clone().add(vec));
        }
        // Random explosions
        Set<Vector> chosen = ThreadLocalRandom.current().nextBoolean() ? huge.alternativeExplosionVectors() : huge.possibleExplosionsVectors();

        for (Vector vec : chosen) {
            explosion.explode(tnt.getLocation().clone().add(vec));
        }

    }
}
