package dev.tom.customtnt.commands.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.tom.customtnt.Util;
import dev.tom.customtnt.commands.TntSubCommand;
import dev.tom.customtnt.commands.arguments.TntArgument;
import dev.tom.customtnt.tnt.TntBuilder;
import dev.tom.customtnt.tnt.TntType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements TntSubCommand {
    @Override
    public CommandAPICommand getSubCommand() {
        return new CommandAPICommand("give")
                .withArguments(TntArgument.tntArgument("type"))
                .withArguments(new IntegerArgument("amount", 1))
                .withOptionalArguments(new PlayerArgument("target"))
                .executes((sender, args) -> {
                    Player target;
                    if(sender == null) {
                        return;
                    }
                    if(args.get("target") == null){
                        target = (Player) sender;
                    } else {
                        target = (Player) args.get("target");
                    }
                    if(target == null) {
                        sender.sendRichMessage("<red>Invalid target</red>");
                        return;
                    }
                    TntType tntType = (TntType) args.get("type");
                    int amount = (int) args.get("amount");
                    ItemStack itemStack = TntBuilder.getItem(tntType);
                    itemStack.setAmount(amount);
                    target.getInventory().addItem(itemStack);
                });
    }
}
