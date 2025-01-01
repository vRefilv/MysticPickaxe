package refil.mysticpickaxe;

import org.bukkit.configuration.file.FileConfiguration;
import refil.mysticpickaxe.utils.RegisterPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Refilmysticpickaxe extends JavaPlugin {

    private final RegisterPlugin RegisterUtil = new RegisterPlugin();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MysticPickaxe is enabled");
        // Save default config if not exists
        saveDefaultConfig();

        // Print loaded configuration (for testing purposes)
        FileConfiguration config = getConfig();
        String pickaxeType = config.getString("pickaxe.type");
        String miningArea = config.getString("pickaxe.area");

        getLogger().info("Loaded config: Pickaxe Type = " + pickaxeType + ", Mining Area = " + miningArea);
        RegisterUtil.registerPlugin(this); //Register plugin in one line
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
