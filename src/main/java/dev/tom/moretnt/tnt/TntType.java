package dev.tom.moretnt.tnt;

import dev.tom.moretnt.tnt.behaviour.*;

/**
 * Enum representing the different types of TNT available in the plugin
 */
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


    public static TntType fromString(String s){
        for(TntType type : TntType.values()){
            if(type.getFriendlyName().equalsIgnoreCase(s)){
                return type;
            }
        }
        return null;
    }

}
