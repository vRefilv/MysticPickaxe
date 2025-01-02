package refil.mysticpickaxe.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.ChatColor;
import refil.mysticpickaxe.Refilmysticpickaxe;

import java.util.List;
import java.util.stream.Collectors;

public final class PickaxeCreationUtil {
    private PickaxeCreationUtil() {
    }

    public static ItemStack createMysticPickaxe(Refilmysticpickaxe plugin) {
        FileConfiguration config = plugin.getConfig();
        Material material = Material.matchMaterial(config.getString("pickaxe.type", "DIAMOND_PICKAXE"));

        if (material == null) {
            plugin.getLogger().warning("Invalid pickaxe type in config.yml. Defaulting to DIAMOND_PICKAXE.");
            material = Material.DIAMOND_PICKAXE;
        }

        ItemStack pickaxe = new ItemStack(material);
        ItemMeta meta = pickaxe.getItemMeta();

        if (meta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&',
                    config.getString("pickaxe.display_name", "&6Mystic Pickaxe"));
            meta.setDisplayName(displayName);

            List<String> lore = config.getStringList("pickaxe.lore");
            if (lore.isEmpty()) {
                lore = List.of("&7A legendary pickaxe imbued with power.", "&7Can mine faster than the wind.");
            }
            meta.setLore(lore.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList()));

            NamespacedKey key = new NamespacedKey(plugin, "mystic_pickaxe");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");

            pickaxe.setItemMeta(meta);
        }

        return pickaxe;
    }
}
