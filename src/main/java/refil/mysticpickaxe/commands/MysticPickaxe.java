package refil.mysticpickaxe.commands;

import refil.mysticpickaxe.Refilmysticpickaxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MysticPickaxe implements CommandExecutor {
    private final Refilmysticpickaxe plugin;

    public MysticPickaxe(Refilmysticpickaxe plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if player is command sender
        if (sender instanceof Player player) {
            // Create mystic pickaxe
            ItemStack mysticPickaxe = refil.mysticpickaxe.utils.MysticPickaxe.createMysticPickaxe(plugin);

            // Adds mystic pickaxe to inventory
            player.getInventory().addItem(mysticPickaxe);
            player.sendMessage("§aYou've received a mystic pickaxe!");

        } else {
            sender.sendMessage("§cOnly Players can use this command!.");
        }

        return true;
    }
}
