package dev.tom.customtnt;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Util {


    public static void sendParsed(CommandSender p, List<String> m){
        MiniMessage mm = MiniMessage.miniMessage();
        for (String s : m) {
            p.sendMessage(mm.deserialize(s));
        }
    }

    public static void sendParsed(CommandSender p, String... m) {
        sendParsed(p, List.of(m));
    }

    public static void sendParsed(CommandSender p, String m){
        sendParsed(p, new String[]{m});
    }

    public static <T extends Enum<T>> boolean isValidEnum(Class<T> enumClass, String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
