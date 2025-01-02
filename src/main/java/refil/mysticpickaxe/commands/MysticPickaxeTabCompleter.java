package refil.mysticpickaxe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MysticPickaxeTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        // If it's the first argument, show subcommands like "reload" or "give"
        if (args.length == 1) {
            suggestions.add("reload");
            suggestions.add("give");
        }

        // Filter the suggestions to only show those that start with the user's input
        return suggestions.stream()
                .filter(suggestion -> suggestion.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
