package dev.tom.customtnt.tnt;

import dev.tom.customtnt.tnt.behaviour.*;

public enum TntType {

    Huge("Huge", new Huge()),
    Chemical("Chemical", new Chemical()),
    Lethal("Lethal", new Lethal()),
    Incendiary("Incendiary", new Incendiary()),
    Lucky("Lucky", new Lucky()),
    Tactical("Tactical", new Tactical()),
    Mimic("Mimic", new Mimic()),
    Hex("Hex", new Hex()),
    Silent("Silent", new Silent());

    private final String friendlyName;
    private final ExplosionStrategy strategy;

    TntType(String friendlyName, ExplosionStrategy strategy) {
        this.friendlyName = friendlyName;
        this.strategy = strategy;
    }

    public ExplosionStrategy getExplosionStrategy() {
        return strategy;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

}
