package refil.mysticpickaxe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import refil.mysticpickaxe.Refilmysticpickaxe;
import refil.mysticpickaxe.utils.PickaxeCreationUtil;

public class MysticPickaxeCommand implements CommandExecutor {
    private final Refilmysticpickaxe plugin;

    public MysticPickaxeCommand(Refilmysticpickaxe plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /mysticpickaxe <subcommand>");
            sender.sendMessage("§aAvailable subcommands: reload, give");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission("mysticpickaxe.reload")) {
                    sender.sendMessage("§cYou do not have permission to reload the configuration.");
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage("§aConfiguration reloaded successfully.");
                plugin.getLogger().info("Configuration reloaded by " + sender.getName());
                break;

            case "give":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cOnly players can use this command.");
                    return true;
                }
                Player player = (Player) sender;

                // Create Mystic Pickaxe
                ItemStack mysticPickaxe = PickaxeCreationUtil.createMysticPickaxe(plugin);

                // Add the Mystic Pickaxe to the player's inventory
                player.getInventory().addItem(mysticPickaxe);

                player.sendMessage("§aYou've received a Mystic Pickaxe!");
                break;

            default:
                sender.sendMessage("§cUnknown subcommand. Use /mysticpickaxe for help.");
                break;
        }
        return true;
    }
}

