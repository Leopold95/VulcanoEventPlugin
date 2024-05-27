package me.leopold95.vulcano.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class VulcanEventTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1)
            return Arrays.asList("setposition", "start", "addeventdropitem");
        return null;
    }
}
