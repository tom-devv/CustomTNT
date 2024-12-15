package dev.tom.moretnt.commands.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.tom.moretnt.MoreTNT;
import dev.tom.moretnt.Util;
import dev.tom.moretnt.commands.TntSubCommand;

public class ReloadCommand implements TntSubCommand {
    @Override
    public CommandAPICommand getSubCommand() {
        return new CommandAPICommand("reload")
                .executes((sender, args) -> {
                    Util.sendParsed(sender, "<green>MoreTNT: Config reloaded</green>");
                    MoreTNT.getInstance().reloadConfigurationFiles();
                });
    }
}
