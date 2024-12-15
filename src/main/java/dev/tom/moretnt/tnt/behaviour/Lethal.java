package dev.tom.moretnt.tnt.behaviour;

import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.files.ConfigurableTNT;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Lethal implements ExplosionStrategy {
    @Override
    public void explode(TNTPrimed tnt, EntityExplodeEvent e) {
        ConfigurableTNT.Lethal lethal = MoreTNT.getTntSettings().getLethal();
        FastExplosion fastExplosion = new FastExplosion();
        for (int i = 0; i < lethal.fastExplosionsCount(); i++) {
            fastExplosion.explode(tnt, e);
        }
    }
}
