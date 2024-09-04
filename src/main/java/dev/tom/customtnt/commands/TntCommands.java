package dev.tom.customtnt.commands;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class TntCommands  {


    public TntCommands(){
    }

    public void registerCommands(JavaPlugin plugin) {
        new CommandAPICommand("customtnt")
                .withPermission("customtnt.admin")
                .withSubcommands(fetchSubCommands().toArray(new CommandAPICommand[0])).register(plugin);
    }

    private List<CommandAPICommand> fetchSubCommands() {
        List<CommandAPICommand> subs = new ArrayList<>();
        Reflections cmdReflections = new Reflections("dev.tom.customtnt.commands");
        Set<Class<? extends TntSubCommand>> subCommandClasses = cmdReflections.getSubTypesOf(TntSubCommand.class);
        for (Class<? extends TntSubCommand> subCommand : subCommandClasses) {
            TntSubCommand subCommandInstance = null;
            try {
                subCommandInstance = subCommand.getDeclaredConstructor().newInstance();
                subs.add(subCommandInstance.getSubCommand());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return subs;
    }
}
