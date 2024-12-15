package dev.tom.moretnt.commands.arguments;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.tom.moretnt.tnt.TntType;

import java.util.Arrays;

public class TntArgument {

    public static Argument<TntType> tntArgument(String nodeName){

        return new CustomArgument<TntType, String>(new StringArgument(nodeName), (info) -> {
            String tntName = info.input();
            TntType type = TntType.fromString(tntName);
            if(type != null){
                return TntType.valueOf(tntName);
            } else {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(new CustomArgument.MessageBuilder("Invalid TNT type: ").appendArgInput());
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(info ->
                Arrays.stream(TntType.values()).map(TntType::getFriendlyName).toArray(String[]::new)
        ));
    }
}
