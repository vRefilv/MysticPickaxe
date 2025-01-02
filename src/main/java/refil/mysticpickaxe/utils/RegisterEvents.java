package refil.mysticpickaxe.utils;

import refil.mysticpickaxe.Refilmysticpickaxe;
import refil.mysticpickaxe.events.MysticListener;

public final class RegisterEvents {
    private RegisterEvents() {
    }

    public static void registerListeners(Refilmysticpickaxe plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MysticListener(plugin), plugin);
    }
}
