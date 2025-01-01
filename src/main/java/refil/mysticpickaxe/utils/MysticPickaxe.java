package refil.mysticpickaxe.utils;

import org.bukkit.configuration.file.FileConfiguration;
import refil.mysticpickaxe.Refilmysticpickaxe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;
import java.util.stream.Collectors;

public class MysticPickaxe {
    public static ItemStack createMysticPickaxe(Refilmysticpickaxe plugin) {
        FileConfiguration config = plugin.getConfig();

        // Get pickaxe type from config
        String pickaxeType = config.getString("pickaxe.type", "DIAMOND_PICKAXE").toUpperCase();
        Material material;

        switch (pickaxeType) {
            case "DIAMOND_PICKAXE":
                material = Material.DIAMOND_PICKAXE;
                break;
            case "NETHERITE_PICKAXE":
                material = Material.NETHERITE_PICKAXE;
                break;
            default:
                plugin.getLogger().warning("Invalid pickaxe type in config.yml. Defaulting to DIAMOND_PICKAXE.");
                material = Material.DIAMOND_PICKAXE;
        }

        // Create the pickaxe
        ItemStack pickaxe = new ItemStack(material);
        ItemMeta meta = pickaxe.getItemMeta();

        if (meta != null) {
            // Set display name from config
            String displayName = config.getString("pickaxe.display_name", "&6Mystic Pickaxe");
            meta.setDisplayName(displayName.replace("&", "§")); // Convert color codes

            // Set lore from config with a default fallback
            List<String> lore = config.getStringList("pickaxe.lore");
            if (lore.isEmpty()) {
                lore = List.of(
                        "&7A legendary pickaxe imbued with power.",
                        "&7Can mine faster than the wind."
                );
            }
            meta.setLore(lore.stream().map(line -> line.replace("&", "§")).collect(Collectors.toList()));

            // Add Persistent Data
            NamespacedKey key = new NamespacedKey(plugin, "mystic_pickaxe");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");

            pickaxe.setItemMeta(meta);
        }


        return pickaxe;
    }
}