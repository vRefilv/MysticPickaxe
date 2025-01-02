package refil.mysticpickaxe;

import org.bukkit.configuration.file.FileConfiguration;
import refil.mysticpickaxe.utils.RegisterCommands;
import refil.mysticpickaxe.utils.RegisterEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Refilmysticpickaxe extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MysticPickaxe is enabled.");

        // Save default config if not exists
        saveDefaultConfig();

        // Print loaded configuration (for testing purposes)
        FileConfiguration config = getConfig();
        getLogger().info("Loaded config: " + config.saveToString());

        // Register commands and listeners
        RegisterCommands.registerCommands(this);
        RegisterEvents.registerListeners(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MysticPickaxe is disabled.");
    }
}
