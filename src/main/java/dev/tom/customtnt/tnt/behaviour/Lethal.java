package dev.tom.customtnt.tnt.behaviour;

import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.files.ConfigurableTNT;
import org.bukkit.entity.TNTPrimed;

public class Lethal implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt) {
        ConfigurableTNT.Lethal lethal = CustomTNT.getTntSettings().getLethal();
        FastExplosion fastExplosion = new FastExplosion();
        for (int i = 0; i < lethal.fastExplosionsCount(); i++) {
            fastExplosion.explode(tnt);
        }
    }
}
