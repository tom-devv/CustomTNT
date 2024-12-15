package dev.tom.moretnt.commands;

import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TntCommands  {


    public TntCommands(){
    }

    public void registerCommands(JavaPlugin plugin) {
        new CommandAPICommand("moretnt")
                .withPermission("moretnt.admin")
                .withSubcommands(fetchSubCommands().toArray(new CommandAPICommand[0])).register(plugin);
    }

    private List<CommandAPICommand> fetchSubCommands() {
        List<CommandAPICommand> subs = new ArrayList<>();
        Reflections cmdReflections = new Reflections("dev.tom.moretnt.commands");
        Set<Class<? extends TntSubCommand>> subCommandClasses = cmdReflections.getSubTypesOf(TntSubCommand.class);
        for (Class<? extends TntSubCommand> subCommand : subCommandClasses) {
            TntSubCommand subCommandInstance;
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
