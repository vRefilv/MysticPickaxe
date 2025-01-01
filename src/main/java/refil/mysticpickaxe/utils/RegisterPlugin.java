package refil.mysticpickaxe.utils;

import refil.mysticpickaxe.Refilmysticpickaxe;
import refil.mysticpickaxe.commands.MysticPickaxe;
import refil.mysticpickaxe.events.MysticListener;

public class RegisterPlugin {

    public void registerPlugin(Refilmysticpickaxe pl) {
        // Register commands and listeners
        RegisterCommands(pl);
        RegisterListeners(pl);
    }

    public void RegisterCommands(Refilmysticpickaxe pl) {
        pl.getCommand("mysticpickaxe").setExecutor(new MysticPickaxe(pl));
    }

    public void RegisterListeners(Refilmysticpickaxe pl) {
        pl.getServer().getPluginManager().registerEvents(new MysticListener(pl), pl);
    }
}
