package dev.tom.customtnt.commands.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.tom.customtnt.CustomTNT;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.commands.TntSubCommand;

public class ReloadCommand implements TntSubCommand {
    @Override
    public CommandAPICommand getSubCommand() {
        return new CommandAPICommand("reload")
                .executes((sender, args) -> {
                    Util.sendParsed(sender, "<green>CustomTNT: Config reloaded</green>");
                    CustomTNT.getInstance().reloadConfigurationFiles();
                });
    }
}
