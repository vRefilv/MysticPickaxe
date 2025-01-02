package refil.mysticpickaxe.utils;

import refil.mysticpickaxe.Refilmysticpickaxe;
import refil.mysticpickaxe.commands.MysticPickaxeCommand;
import refil.mysticpickaxe.commands.MysticPickaxeTabCompleter;

public final class RegisterCommands {
    private RegisterCommands() {
    }

    public static void registerCommands(Refilmysticpickaxe plugin) {
        plugin.getCommand("mysticpickaxe").setExecutor(new MysticPickaxeCommand(plugin));
        plugin.getCommand("mysticpickaxe").setTabCompleter(new MysticPickaxeTabCompleter());
    }
}
